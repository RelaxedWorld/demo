package com.example.demo.controller;

import com.example.demo.constants.BaseConstants;
import com.example.demo.domains.Result;
import com.example.demo.service.UserService;
import com.example.demo.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result userLogin(String userName, String userPwd,
                            HttpServletRequest request, HttpServletResponse response) {
        try {
            Result result = userService.userLogin(userName, userPwd, request, response);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.getErrorResult(500, e.getMessage());
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        userService.logout(request); // 思路是从Redis中删除key，实际情况请和业务逻辑结合
        return "index";
    }

    @RequestMapping("/userInfo")
    public Result getUserByToken(HttpServletRequest request) {
        Result result = null;
        try {
            result = userService.queryUserByToken(CookieUtils.getCookieValue(request, BaseConstants.TOKEN));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.getErrorResult(500, e.getMessage());
        }
        return result;
    }
}
