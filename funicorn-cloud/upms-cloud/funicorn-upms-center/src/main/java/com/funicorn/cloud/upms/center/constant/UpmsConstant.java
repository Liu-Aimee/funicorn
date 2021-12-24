package com.funicorn.cloud.upms.center.constant;

/**
 * @author Aimee
 * @since 2021/4/26 8:26
 */
@SuppressWarnings("unused")
public class UpmsConstant {

    /** 超级租户id */
    public static final String SUPER_TENANT_ID = "-1";

    /** 超级管理员类别 */
    public static final Integer TENANT_USER_SUPER = -1;
    /** 租户普通用户类别 */
    public static final Integer TENANT_USER_NORMAL = 0;
    /** 租户管理员类别 */
    public static final Integer TENANT_USER_ADMIN = 1;

    /** 新建用户默认密码 */
    public static final String USER_INIT_PASSWORD = "Aa123456";
    /** 超级管理员角色编码 */
    public static final String ROLE_SUPER_ADMIN = "ROLE_super_admin";
    /** 租户管理员角色编码 */
    public static final String ROLE_TENANT_ADMIN = "ROLE_tenant_admin";

    /** 未软删标识 */
    public static final String NOT_DELETED = "0";
    /** 已软删标识 */
    public static final String IS_DELETED = "1";

    /** 可用 */
    public static final Integer ENABLED = 0;
    /** 不可用 */
    public static final Integer DISABLED = 1;
    /** 开通中 */
    public static final Integer APP_APPLYING = 2;
    /** 拒绝开通 */
    public static final Integer REFUSE_APP_APPLY = 3;

    /**菜单*/
    public static final String TYPE_MENU = "menu";
    /**按钮*/
    public static final String TYPE_BUTTON = "button";
}
