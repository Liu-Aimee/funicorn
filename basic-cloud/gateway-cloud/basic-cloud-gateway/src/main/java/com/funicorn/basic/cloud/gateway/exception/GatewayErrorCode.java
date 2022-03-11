package com.funicorn.basic.cloud.gateway.exception;

/**
 * @author Aimee
 * @since 2021/9/28 10:49
 * -100 ~ -199
 */
@SuppressWarnings("unused")
public enum GatewayErrorCode {

    /**
     * 转发地址已存在
     * */
    URI_IS_EXISTS(-100,"转发地址已被占用"),

    /**
     * 转发规则已存在
     * */
    PREDICATES_IS_EXISTS(-101,"转发规则已被占用"),

    /**
     * 转发规则已存在
     * */
    ROUTE_IS_NOT_FOUND(-102,"路由未找到"),

    /**
     * 转发规则已存在
     * */
    PREDICATES_IS_NOT_FOUND(-103,"未配置断言"),
            ;

    private final int status;
    private final String message;

    GatewayErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }
}
