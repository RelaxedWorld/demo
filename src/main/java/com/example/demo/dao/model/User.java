package com.example.demo.dao.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class User {
    private Long id;

    private String userName;

    private String userPwd;

    private Date createDate;

    private Date modifyDate;
}