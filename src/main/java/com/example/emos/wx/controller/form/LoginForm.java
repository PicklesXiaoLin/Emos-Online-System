package com.example.emos.wx.controller.form;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel
public class LoginForm {

    // web端、定义Form封装 ajax 的请求
    // 私有的值，需要通过getCode（）来获取对应的值
    // 共有的值，可以直接form.code 获取
    @NotBlank(message = "临时授权不能为空")
    private String code;

}
