package com.example.demo.domains.enums;

public enum ResultEnum {
    USER_NOT_EXIT(3000,"用户不存在"),
    USERNAME_NOT_NULL(3001,"用户名不能为空."),
    LOGIN_TOKEN_NOT_NULL(3002,"登录token不能为空."),
    USERID_NOT_UNAUTHORIZED(3003, "用户token或ID验证不通过"),
    RESPONSE_CODE_UNLOGIN_ERROR(421, "未登录异常"),
    LOGIN_TIME_EXP(3004, "登录时间超长，请重新登录");

    // 成员变量
    private int code; //状态码
    private String message; //返回消息

    // 构造方法
    private ResultEnum(int code,String message) {
        this.code = code;
        this.message = message;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
