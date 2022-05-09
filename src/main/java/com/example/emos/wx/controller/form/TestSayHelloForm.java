package com.example.emos.wx.controller.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@ApiModel
@Data
public class TestSayHelloForm {
//    @NotBlank
//    @Pattern(regexp = "[\\u4e00-\\u9fa5]{2,15}$")
    @ApiModelProperty("名字")
    private String name;

    @ApiModelProperty("年龄")
    private String age;

    @ApiModelProperty("性别")
    private String sex;
}
