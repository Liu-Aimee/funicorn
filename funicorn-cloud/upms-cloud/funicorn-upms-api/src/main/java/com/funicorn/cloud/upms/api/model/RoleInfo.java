package com.funicorn.cloud.upms.api.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 角色信息
 * @author Aimee
 * @since 2021/1/26 18:06
 */
@Data
public class RoleInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     * */
    private String id;
    /**
     * 角色名称
     * */
    private String name;

    /**
     * 角色编号
     * */
    private String code;

    /**
     * 租户id
     * */
    private String tenantId;

    /**
     * 关联用户数量
     * */
    private Integer userNum;

    /**
     * 0 未删除 1已删除
     * */
    private String isDelete;
}
