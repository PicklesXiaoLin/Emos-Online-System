package com.example.emos.wx.exception;

import lombok.Data;

//使用lombok的set（）方法
@Data
public class EmosException extends RuntimeException{
    private String msg;
    private int code = 500;

    //子类构造器会传递父类构造器，所以用super（）传递参数
    public EmosException(String msg){
        super(msg);
        this.msg = msg;
    }

    public EmosException(String msg, Throwable e){
        super(msg,e);
        this.msg = msg;
    }

    public EmosException(String msg, int code){
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public EmosException(String msg, int code, Throwable e){
        super(msg,e);
        this.msg = msg;
        this.code = code;
    }

}
