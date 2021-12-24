package com.funicorn.basic.common.base.model;

/**
 * @author Aimee
 * @since 2021/10/27 10:31
 */
public interface UserDetail {

    /**
     * 操作人id
     * @return userId
     * */
    String getUserId();

    /**
     * 操作人用户名
     * @return username
     * */
    String getUsername();

    /**
     * 租户id
     * @return 租户id
     * */
    String getTenantId();
}
