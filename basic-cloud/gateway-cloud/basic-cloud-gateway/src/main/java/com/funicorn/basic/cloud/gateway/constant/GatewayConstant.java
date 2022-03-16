package com.funicorn.basic.cloud.gateway.constant;

import java.util.Arrays;
import java.util.List;

/**
 * @author Aimee
 * @since 2021/10/27 13:15
 */
public class GatewayConstant {

    public static final String  GATEWAY_ROUTES = "gateway:routes";

    public static final String ROUTE_STATUS_ON = "1";
    public static final String ROUTE_STATUS_OFF = "0";

    /**
     * 已删
     * */
    public static final String IS_DELETED = "1";

    /**
     * 未删
     * */
    public static final String NOT_DELETED = "0";

    /**允许有多个值的断言类型*/
    public static final List<String> PREDICATE_SUPPORT_MORE_DATA_TYPE = Arrays.asList("Path","Method","Host","RemoteAddr");
}
