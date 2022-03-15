package com.funicorn.cloud.system.center.constant;

import java.util.Arrays;
import java.util.List;

/**
 * @author Aimee
 * @since 2021/10/15 9:59
 */
@SuppressWarnings("unused")
public class SystemConstant {

    /**
     * 已删
     * */
    public static final String IS_DELETED = "1";

    /**
     * 未删
     * */
    public static final String NOT_DELETED = "0";

    /** 超级管理员类别 */
    public static final Integer TENANT_USER_SUPER = -1;
    /** 租户普通用户类别 */
    public static final Integer TENANT_USER_NORMAL = 0;
    /** 租户管理员类别 */
    public static final Integer TENANT_USER_ADMIN = 1;

    public static final String ROUTE_STATUS_ON = "1";
    public static final String ROUTE_STATUS_OFF = "0";

    /**允许有多个值的断言类型*/
    public static final List<String> PREDICATE_SUPPORT_MORE_DATA_TYPE = Arrays.asList("Path","Method");

    /**允许有多个值的断言类型*/
    public static final String PREDICATE_TYPE_METHOD = "Method";

}
