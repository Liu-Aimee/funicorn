package com.funicorn.basic.common.security.model;

import com.funicorn.basic.common.base.model.UserDetail;
import lombok.Data;

/**
 * @author Aimee
 * @since 2021/11/9 11:43
 */
@Data
public class ContextUser implements UserDetail {

    /**
     * 用户id
     * */
    private String userId;

    /**
     * 用户名
     * */
    private String username;

    /**
     * 租户id
     * */
    private String tenantId;

    @Override
    public String getUserId() {
        return this.userId;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getTenantId() {
        return this.tenantId;
    }
}
