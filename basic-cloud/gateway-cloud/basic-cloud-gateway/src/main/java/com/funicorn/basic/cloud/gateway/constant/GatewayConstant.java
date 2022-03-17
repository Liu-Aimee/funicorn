package com.funicorn.basic.cloud.gateway.constant;

import java.util.Arrays;
import java.util.List;

/**
 * @author Aimee
 * @since 2021/10/27 13:15
 */
@SuppressWarnings("unused")
public class GatewayConstant {

    /**路由缓存key*/
    public static final String  GATEWAY_ROUTES = "gateway:routes";

    /**已启用*/
    public static final String ROUTE_STATUS_ON = "1";
    /**未启用*/
    public static final String ROUTE_STATUS_OFF = "0";

    /**已删*/
    public static final String IS_DELETED = "1";

    /**未删*/
    public static final String NOT_DELETED = "0";

    /**允许有多个值的断言类型*/
    public static final List<String> PREDICATE_SUPPORT_MORE_DATA_TYPE = Arrays.asList("Path","Method","Host","RemoteAddr");

    /**不需要参数的过滤器类型*/
    public static final List<String> FILTER_NOT_HAS_ARGS_TYPES = Arrays.asList("PreserveHostHeader","SaveSession");
}
