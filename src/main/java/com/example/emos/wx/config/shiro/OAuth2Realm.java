package com.example.emos.wx.config.shiro;

import com.example.emos.wx.db.pojo.TbUser;
import com.example.emos.wx.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class OAuth2Realm extends AuthorizingRealm {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {

        return token instanceof OAuth2Token;
    }

    /*
     * 授权（验证权限时调用）
     */
    //ctrl + shift + / 快速注解
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection collection) {
        TbUser user = (TbUser) collection.getPrimaryPrincipal();
        int userId = user.getId();

        // TODO 查询用户的权限列表
        Set<String> permsSet = userService.searchUserPermissions(userId);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        // TODO 把权限列表添加到info String
        info.setStringPermissions(permsSet);

        return info;
    }

    /*
    * 认证（登录验证时调用)
    */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // TODO 从令牌中获取userId， 检测该账户是否被冻结
        String accessToken = (String)token.getPrincipal();
        int userId = jwtUtil.getUserId(accessToken);
        TbUser user = userService.searchById(userId);

        if(user==null){
            throw new LockedAccountException("该账号已被锁定，请联系管理员");
        }
        // 将用户的基本信息，token，reaml类传入认证方法
        // 颁发认证对象
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,accessToken,getName());
        // TODO 往info对象添加用户信息、Token字符串
        return info;
    }
}
