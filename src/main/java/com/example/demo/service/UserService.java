package com.example.demo.service;

import com.example.demo.domains.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2018/7/19.
 */
public interface UserService {
    Result userLogin(String userName, String userPwd, HttpServletRequest request, HttpServletResponse response);
    void logout(HttpServletRequest request);
    Result queryUserByToken(String token);
}
