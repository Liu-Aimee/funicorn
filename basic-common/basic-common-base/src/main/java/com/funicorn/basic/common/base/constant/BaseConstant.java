package com.funicorn.basic.common.base.constant;

/**
 * @author Aimee
 * @since 2021/10/21 13:50
 */
public class BaseConstant {

    /**
     * 令牌传输key
     * */
    public static final String ACCESS_TOKEN = "Authorization";

    /**
     * HEADER_WHERE_CALL_KEY
     * */
    public static final String HEADER_WHERE_CALL_KEY = "Where-Call";

    /**
     * INNER_CALL
     * */
    public static final String INNER_CALL = "INNER";

    /**
     * OUTER_CALL
     * */
    public static final String OUTER_CALL = "OUTER";

    /**
     * 字符编码格式
     * */
    public static final String CHARSET_UTF8 = "utf-8";

    /**
     * 单机模式
     * */
    public static final String REDIS_MODEL_STANDALONE = "standalone";

    /**
     * 集群模式
     * */
    public static final String REDIS_MODEL_CLUSTER = "cluster";

    /**
     * 哨兵模式
     * */
    public static final String REDIS_MODEL_SENTINEL = "sentinel";
}
