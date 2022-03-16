package com.funicorn.basic.gateway.api.constant;

import java.util.Arrays;
import java.util.List;

/**
 * @author Aimee
 * @since 2022/3/16 10:07
 */
public class RouteConstant {

    /**允许有多个值的断言类型*/
    public static final List<String> PREDICATE_SUPPORT_TYPE = Arrays.asList("After","Before","Between","Cookie","Header","Path","Method","Host","RemoteAddr","Query");
}
