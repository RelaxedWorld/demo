package com.example.demo.controller;

import com.example.demo.domains.Result;
import com.example.demo.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/login")
    @ResponseBody
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
    @ResponseBody
    public String logout(HttpServletRequest request) {
        userService.logout(request); // 思路是从Redis中删除key，实际情况请和业务逻辑结合
        return "index";
    }

    @RequestMapping("/token/{token}")
    @ResponseBody
    public Object getUserByToken(@PathVariable String token) {
        Result result = null;
        try {
            result = userService.queryUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.getErrorResult(500, e.getMessage());
        }
        return result;
    }
}
