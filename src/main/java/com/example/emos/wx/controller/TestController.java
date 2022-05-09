package com.example.emos.wx.controller;

import com.example.emos.wx.common.util.R;
import com.example.emos.wx.controller.form.TestSayHelloForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/test")
@Api("测试Web接口")

public class TestController {
    @PostMapping("/sayHello")
    @ApiOperation("最简单的测试方法")
    public R sayHello(@Valid @RequestBody TestSayHelloForm form){
        return R.ok().put("message","Hello"+form.getName()+","+form.getAge()+","+form.getSex());
    }

    @PostMapping("/sayError")
    @ApiOperation("最简单的测试方法2")
    public R sayError(@Valid @RequestBody TestSayHelloForm form){
        return R.ok().put("ERROR.message","Hello"+form.getName()+","+form.getAge());
    }

    @PostMapping("/addUser")
    @ApiOperation("添加用户")
//    @RequiresPermissions(value = {"A","B"},logical = Logical.OR)
    @RequiresPermissions(value = {"ROOT","USER:ADD"},logical = Logical.OR)
    public R adduser(){
        return R.ok("用户添加成功~");
    }
}
