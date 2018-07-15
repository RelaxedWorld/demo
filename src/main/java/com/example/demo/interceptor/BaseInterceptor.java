package com.example.demo.interceptor;

import com.example.demo.constants.BaseConstants;
import com.example.demo.domains.Result;
import com.example.demo.service.impl.UserServiceImpl;
import com.example.demo.utils.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class BaseInterceptor implements HandlerInterceptor {
    @Autowired
    private UserServiceImpl userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        response.setCharacterEncoding(BaseConstants.UTF8);
        //登录和公开的不做拦截
        if (request.getRequestURI().contains("login") || request.getRequestURI().contains("pub")) {
            return true;
        }
        String token = CookieUtils.getCookieValue(request, BaseConstants.TOKEN);
        //token不存在
        if (StringUtils.isEmpty(token)) {
            return false;
        }
        //查看token是否存在,如果存在则刷新缓存时间
        Result result = userService.queryUserByToken(token);
        if (!result.isSuccess()) {
            return false;
        } else {
            return true;
        }
    }
}