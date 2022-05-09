package com.example.emos.wx.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.emos.wx.db.dao.TbUserDao;
import com.example.emos.wx.db.pojo.TbUser;
import com.example.emos.wx.exception.EmosException;
import com.example.emos.wx.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;

@Service
@Slf4j
@Scope("prototype")
public class UserServiceImpl implements UserService {

    @Value("${wx.app-secret}")
    private String appSecret;

    @Value("${wx.app-id}")
    private String appId;

    @Autowired
    private TbUserDao userDao;

    private String getOpenId(String code){
        String url="https://api.weixin.qq.com/sns/jscode2session";
        HashMap map = new HashMap();
        map.put("appid",appId);
        map.put("secret",appSecret);
        map.put("js_code",code);
        map.put("grant_type","authorization_code");
        String response = HttpUtil.post(url, map);
        JSONObject json = JSONUtil.parseObj(response);
        String openId = json.getStr("openid");
        if(openId == null || openId.length()==0){
            throw new RuntimeException("临时登录凭证错误！！！");
        }
        return openId;
    }

    @Override
    public int registerUser(String registerCode, String code, String nickname, String photo) {
        System.out.println(registerCode);
        System.out.println(code);
        System.out.println(nickname);
        System.out.println(photo);
        if(registerCode.equals("000000")){
            boolean bool = userDao.haveRootUser();
            if(!bool){
                // 传入激活码
                String openId = getOpenId(code);
                HashMap param = new HashMap();
                param.put("openId",openId);
                param.put("nickname",nickname);
                param.put("photo",photo);
                param.put("role","[0]");
                param.put("status",1);
                param.put("createTime",new Date());
                param.put("root",true);
                // 将管理员信息插入到数据库中
                userDao.insert(param);
                // 查询插入的信息对应的ID号(主键值）
                int id = userDao.searchIdByOpenId(openId);
                return id;
            }else{
                // Emos异常主要针对业务、runtime主要针对平台
                throw new EmosException("无法绑定超级管理员账号！");
            }
        }

        return 0;
    }

    @Override
    public Set<String> searchUserPermissions(int userId) {
        Set<String> permission = userDao.searchUserPermissions(userId);
        return permission;
    }


    // 接口的实现类
    @Override
    public Integer login(String code) {
        String openId = getOpenId(code);
        // 从TbuserDao里面取方法，查找该激活码对应的openId是否注册过
        Integer id = userDao.searchIdByOpenId(openId);
        if(id == null){
            throw new EmosException("账户不存在");
        }
        // TODO 如果id存在，则返回消息队列到已存在的用户
        // 给web端做令牌的生成
        return id;
    }

    @Override
    public TbUser searchById(int userId) {
        TbUser user = userDao.searchById(userId);
        return user;
    }

    @Override
    public String searchUserHiredate(int userId) {
        String hiredate = userDao.searchUserHiredate(userId);
        return hiredate;
    }
}
