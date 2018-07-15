package com.example.demo.service.impl;

import com.example.demo.domains.Result;
import com.example.demo.domains.User;
import com.example.demo.domains.enums.ResultEnum;
import com.example.demo.service.JedisService;
import com.example.demo.utils.CookieUtils;
import com.example.demo.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Service
//@Transactional
public class UserServiceImpl {
    @Autowired
    private JedisService jedisService;
    @Value("${REDIS_USER_SESSION_KEY}")
    private String REDIS_USER_SESSION_KEY;
    @Value("${SSO_SESSION_EXPIRE}")
    private Integer SSO_SESSION_EXPIRE;

    public Result userLogin(String userName, String userPwd,
                            HttpServletRequest request, HttpServletResponse response) {
        //校验用户信息
        Result result = validateUser(userName, userPwd);
        if (!result.isSuccess()) {
            return result;
        }
        // 判断账号密码是否正确
        User user = new User();
        // 清空密码和盐避免泄漏
        user.setUserName(userName);
        // 生成token
        String token = UUID.randomUUID().toString();
        // 把用户信息写入 redis
        jedisService.set(REDIS_USER_SESSION_KEY + ":" + token, JsonUtils.objectToJson(user));
        // user 已经是持久化对象，被保存在session缓存当中，若user又重新修改属性值，那么在提交事务时，此时 hibernate对象就会拿当前这个user对象和保存在session缓存中的user对象进行比较，如果两个对象相同，则不会发送update语句，否则会发出update语句。
        // 设置 session 的过期时间
        jedisService.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);
        // 添加写 cookie 的逻辑，cookie 的有效期是关闭浏览器就失效。
        CookieUtils.setCookie(response, "token", token);
        // 返回token
        return Result.getSuccessResult();
    }

    public void logout(HttpServletRequest request) {
        String token = CookieUtils.getCookieValue(request, "token");
        jedisService.del(REDIS_USER_SESSION_KEY + ":" + token);
    }

    public Result queryUserByToken(String token) {
        // 根据token从redis中查询用户信息
        String json = jedisService.get(REDIS_USER_SESSION_KEY + ":" + token);
        // 判断是否为空
        if (StringUtils.isEmpty(json)) {
            return Result.getErrorResult(ResultEnum.LOGIN_TOKEN_NOT_NULL);
        }
        // 更新过期时间
        jedisService.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);
        // 返回用户信息
        return Result.getSuccessResult();
    }

    private Result validateUser(String userName, String userPwd) {
        if (StringUtils.isEmpty(userName)) {
            return Result.getErrorResult(ResultEnum.USERNAME_NOT_NULL);
        }
        if (StringUtils.isEmpty(userPwd)) {
            return Result.getErrorResult(ResultEnum.USERNAME_NOT_NULL);
        }
        if (!("zsy".equals(userName) && "zsy".equals(userPwd))) {
            return Result.getErrorResult(ResultEnum.USER_NOT_EXIT);
        }
        return Result.getSuccessResult();
    }
}
