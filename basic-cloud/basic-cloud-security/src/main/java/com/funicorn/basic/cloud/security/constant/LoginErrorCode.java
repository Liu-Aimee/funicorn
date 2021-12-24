package com.funicorn.basic.cloud.security.constant;

/**
 * 登录失败原因枚举
 * @author Aimee
 * @since 2021/10/23 9:57
 */

public enum LoginErrorCode {

    /**
     * 账号过期
     * */
    USER_ACCOUNT_EXPIRED("账号过期"),

    /**
     * 密码错误
     * */
    USER_CREDENTIALS_ERROR("密码错误"),

    /**
     * 密码过期
     * */
    USER_CREDENTIALS_EXPIRED("密码过期"),

    /**
     * 账号不可用
     * */
    USER_ACCOUNT_DISABLE("账号不可用"),

    /**
     * 账号被锁定
     * */
    USER_ACCOUNT_LOCKED("账号被锁定"),

    /**
     * 用户不存在
     * */
    USER_ACCOUNT_NOT_EXIST("用户不存在"),

    /**
     * 其他错误
     * */
    COMMON_FAIL("其他错误"),

    ;

    private final String message;

    LoginErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
