package com.funicorn.cloud.upms.api.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户与租户关系
 * @author Aimee
 * @since 2021/5/7 10:05
 */
@Data
public class UserTenInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     * */
    private String id;

    /**
     * 用户id
     * */
    private String userId;

    /**
     * 用户名账号
     * */
    private String username;

    /**
     * 用户类别 -1:超级管理员,0:普通用户,1:租户管理员
     * */
    private Integer type;

    /**
     * 租户id
     * */
    private String tenantId;

    /**
     * 租户名称
     * */
    private String tenantName;
}
