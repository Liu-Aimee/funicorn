package com.funicorn.basic.common.base.exception;

/**
 * @author Aimee
 * @since 2021/4/22 9:44
 * -1~-99
 */

@SuppressWarnings(value = {"unused"})
public enum BaseErrorCode {

    /**
     * 系统异常,请联系管理员
     * */
    SYSTEM_ERROR(-1, "系统异常,请联系管理员"),

    /**
     * Invalid token
     * */
    INVALID_TOKEN(-2,"Invalid access_token!"),

    /**
     * 无权访问
     * */
    NO_PERMISSION(-3,"无权访问"),

    /**
     * 登录失败
     * */
    LOGIN_FAIL(-4,"登录失败[%s]"),

    /**
     * 入参不正确
     * */
    PARAM_IS_INVALID(-5,"入参不正确[%s]"),

    /**
     * 请求类型不正确
     * */
    REQUEST_METHOD_NOT_SUPPORTED(-6,"请求类型不正确[%s]"),

    /**
     * 请求类型不正确
     * */
    CONTENT_TYPE_NOT_SUPPORTED(-7,"内容类型不支持[%s]"),

    /**
     * redis节点不够
     * */
    REDIS_CLUSTER_NODES_ERROR(-10, "redis集群模式最少6个节点"),

    /**
     * 节点数量不够或未配置节点
     * */
    REDIS_NODES_ERROR(-11, "节点数量不够或未配置节点"),

    /**
     * 节点配置有误
     * */
    REDIS_ONE_NODE_ERROR(-12, "节点配置有误[%s]"),

    /**
     * 未配置redis主节点名称
     * */
    REDIS_MASTER_NAME_ERROR(-13,"未配置redis主节点名称"),
    ;

    private final int status;
    private final String message;

    BaseErrorCode(int status, String message) {
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
