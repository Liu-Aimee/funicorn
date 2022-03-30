package com.funicorn.basic.gateway.api.constant;

import java.util.Arrays;
import java.util.List;

/**
 * @author Aimee
 * @since 2022/3/16 10:07
 */
public class RouteConstant {

    /**支持的断言类型*/
    public static final List<String> PREDICATE_SUPPORT_TYPE = Arrays.asList("After","Before","Between","Cookie","Header","Path","Method","Host","RemoteAddr","Query");

    /**支持的过滤器类型*/
    public static final List<String> FILTER_SUPPORT_TYPE = Arrays.asList("AddRequestHeader","AddRequestParameter","AddResponseHeader","PrefixPath","PreserveHostHeader",
            "RedirectTo","RemoveRequestHeader","RemoveResponseHeader","RewritePath","RewriteResponseHeader",
            "SaveSession","SetPath","SetResponseHeader","SetStatus","StripPrefix");
}
