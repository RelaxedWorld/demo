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
import java.io.IOException;

@Component
@Slf4j
public class BaseInterceptor implements HandlerInterceptor {
    @Autowired
    private UserServiceImpl userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        response.setCharacterEncoding(BaseConstants.UTF8);
        //登录和公开的不做拦截
        try {
            if (request.getRequestURI().contains("login") || request.getRequestURI().contains("pub") || request.getRequestURI().contains("static")) {
                return true;
            }
            String token = CookieUtils.getCookieValue(request, BaseConstants.TOKEN);
            //token不存在
            if (StringUtils.isEmpty(token)) {
                redirect(request, response);
                return false;
            }
            //查看token是否存在,如果存在则刷新缓存时间
            Result result = userService.queryUserByToken(token);
            if (!result.isSuccess()) {
                redirect(request, response);
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            log.error("preHandle=" + e.getMessage());
        }
        return true;
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取当前请求的路径
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
//        response.getOutputStream().write("账号在别处登录。".getBytes("UTF-8"));
        //如果request.getHeader("X-Requested-With") 返回的是"XMLHttpRequest"说明就是ajax请求，需要特殊处理 否则直接重定向就可以了
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            //告诉ajax我是重定向
            response.setHeader("REDIRECT", "REDIRECT");
            //告诉ajax我重定向的路径
            response.setHeader("CONTENTPATH", basePath + "/static/index.html");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            response.sendRedirect(basePath + "/static/index.html");
        }
    }
}