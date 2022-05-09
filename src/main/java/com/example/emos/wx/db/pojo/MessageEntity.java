package com.example.emos.wx.db.pojo;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Data
@Document(collection = "message")
public class MessageEntity implements Serializable {

    @Id
    private String _id;

    @Indexed(unique = true)
    private String uuid;

    @Indexed
    private Integer senderId;

    private String senderPhoto = "https://img1.baidu.com/it/u=2221673110,497982984&fm=253&fmt=auto&app=138&f=JPEG?w=640&h=457";

    private String senderName;

    private String msg;

    @Indexed
    private Date sendtime;

}
