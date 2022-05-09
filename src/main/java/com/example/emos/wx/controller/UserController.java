package com.example.emos.wx.controller;

import com.example.emos.wx.common.util.R;
import com.example.emos.wx.config.shiro.JwtUtil;
import com.example.emos.wx.controller.form.LoginForm;
import com.example.emos.wx.controller.form.RegisterForm;
import com.example.emos.wx.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Api("用户模块web接口")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${emos.jwt.cache-expire}")
    private int cacheExpire;

    @PostMapping("/register")
    @ApiOperation("注册用户")
    public R register(@Valid @RequestBody RegisterForm form){

        // 获取到用户的注册码、id、昵称、头像
        System.out.println(form);
        int id = userService.registerUser(form.getRegisterCode(), form.getCode(),form.getNickname(),form.getPhoto());
        // 将用户的ID编码为Token的JSON
        String token = jwtUtil.createToken(id);
        // 将用户的权限SET集
        Set<String> permsSet = userService.searchUserPermissions(id);
        saveCacheToken(token,id);
        return R.ok("用户注册成功").put("token",token).put("permission",permsSet);
    }

    @PostMapping("/login")
    @ApiOperation("登录系统")
    public R login(@Valid @RequestBody LoginForm form){
        int id = userService.login(form.getCode());
        // 创建令牌字符串
        String token = jwtUtil.createToken(id);
        // 缓存 token id
        saveCacheToken(token,id);
        // 获取权限
        Set<String> permsSet= userService.searchUserPermissions(id);
        return R.ok("登录成功").put("token",token).put("permission",permsSet);
    }


    // 定义一个私有方法，返回数据到Redis数据库
    private void saveCacheToken(String token, int userId){
        redisTemplate.opsForValue().set(token,userId+"",cacheExpire, TimeUnit.DAYS);
    }
}
