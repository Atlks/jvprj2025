package util.model.pwd;

package com.example.model;

/**
 * 密码类型枚举
 */
public enum PasswordType {

    LOGIN_PASSWORD("LOGIN", "登录密码"),
    TRADE_PASSWORD("TRADE", "交易密码"),
    WITHDRAW_PASSWORD("WITHDRAW", "提现密码"),
    FUND_PASSWORD("FUND", "资金密码"),
    SECURITY_QUESTION_PASSWORD("SECURITY_QUESTION", "安全问题密码"),
    OPERATION_CONFIRM_PASSWORD("OPERATION_CONFIRM", "操作确认密码"),
    API_ACCESS_PASSWORD("API_ACCESS", "API访问密码"),
    OTP_PASSWORD("OTP", "动态密码"),
    DEVICE_UNLOCK_PASSWORD("DEVICE_UNLOCK", "设备解锁密码"),
    AUTHORIZATION_PASSWORD("AUTHORIZATION", "授权密码");

    private final String code;
    private final String description;

    PasswordType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
