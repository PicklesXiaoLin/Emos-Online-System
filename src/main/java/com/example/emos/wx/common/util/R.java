package com.example.emos.wx.common.util;


import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class R extends HashMap<String, Object> {
    public R(){
        put("code", HttpStatus.SC_OK);
        put("msg","success");
    }

    //haspymap已经有put方法，希望可以链式调用
    //绑定R对象的put方法，链式
    public R put(String key,Object value){
        super.put(key,value);
        return this;
    }
    //绑定工厂方法
    public static R ok(){
        return new R();
    }
    //重载
    public static R ok(String msg){
        R r = new R();
        r.put("msg",msg);
        return r;
    }
    //返回map对象
    public static R ok(Map<String, Object> map){
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R error(int code,String msg){
        R r = new R();
        r.put("code",code);
        r.put("msg",msg);
        return r;
    }
    public static R error(String msg){
        //重调用上面的error
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR,msg);
    }
    public static R error(){
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR,"未知异常，请联系管理员");
    }

}
