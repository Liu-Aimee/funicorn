package com.funicorn.cloud.chart.center.exception;

/**
 * @author Aimme
 * -2000 ~ -2999
 */
@SuppressWarnings("unused")
public enum ErrorCode {

    /**未设置属性标记*/
    NOT_SET_SIGN(-2000, "未设置属性标记"),

    /**数据库连接失败*/
    SQL_CONNECTION_FAIL(-2001, "数据库连接失败"),

    /**数据库连接失败*/
    CHART_TYPE_IS_EXISTS(-2002, "图表类型已存在[%s]"),

    /**加载数据源连接池失败*/
    INIT_DATA_SOURCE_FAIL(-2003, "加载数据源连接池失败"),

    /**数据集不存在*/
    NOT_FOUND_DATA_SET(-2004, "数据集不存在[%s]"),

    /**函数不存在*/
    NOT_FOUND_FUNCTION(-2005, "函数不存在[%s]"),

    /**函数不存在*/
    NOT_FOUND_DATA_SOURCE(-2006, "数据源不存在[%s]"),

    /**未传入参或入参不合法*/
    PARAMS_IS_INVALID(-2007, "未传入参或入参不合法"),

    /**未传入参或入参不合法*/
    PARAM_VALUE_IS_NOT_FOUND(-2008, "[%s]参数未传值"),

    /**未传入参或入参不合法*/
    REQUEST_EXCEPTION(-2009, "HTTP请求异常,[%s]"),

    /**未传入参或入参不合法*/
    REQUEST_FAILED(-20010, "HTTP请求失败,[%s]"),
    ;

    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
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
