package com.example.emos.wx.db.dao;

import com.example.emos.wx.db.pojo.TbUser;
import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.Set;

@Mapper
public interface TbUserDao {
    public boolean haveRootUser();
    public int insert(HashMap param);
    public Integer searchIdByOpenId(String openId);

    // 返回的字符串不要重复的
    public Set<String> searchUserPermissions(int userId);
    public TbUser searchById(int userId);

    public HashMap searchNameAndDept(int userId);
    public String searchUserHiredate(int userId) ;
}