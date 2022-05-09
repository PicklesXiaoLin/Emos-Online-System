package com.example.emos.wx.service;

import com.example.emos.wx.db.pojo.TbUser;

import java.util.Set;

public interface UserService {
    public int registerUser(String registerCode, String code, String nickname, String photo);
//    public int register( String registerCode , String code , String nickname ,String photo ) ;
    public Set<String> searchUserPermissions(int userId);

    // 打开一个接口，用于接收临时授权码
    public Integer login(String code);

    public TbUser searchById(int userId);

    public String searchUserHiredate(int userId);
}
