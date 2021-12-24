package com.funicorn.cloud.upms.center.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Aimee
 * @since 2021/1/26 18:06
 */
@Data
public class RoleVO implements Serializable {

    private static final long serialVersionUID=1L;

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
     * 创建时间
     * */
    private Date createdTime;
}
