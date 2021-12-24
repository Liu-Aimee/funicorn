package com.funicorn.cloud.upms.center.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Aimee
 * @since 2021/10/20 14:25
 */
@Data
public class UserTenantDTO implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 用户id
     * */
    private String userId;

    /**
     * 租户id
     * */
    private String tenantId;
}
