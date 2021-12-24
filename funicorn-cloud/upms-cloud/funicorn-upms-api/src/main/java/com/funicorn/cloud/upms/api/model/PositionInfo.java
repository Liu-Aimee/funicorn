package com.funicorn.cloud.upms.api.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 职务信息
 * @author Aimee
 * @since 2021/2/2 11:19
 */
@Data
public class PositionInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     * */
    private String userId;

    /**
     * 主键id
     * */
    private String id;

    /**
     * 职位名称
     * */
    private String name;

    /**
     * 备注
     * */
    private String remark;

    /**
     * 租户id
     * */
    private String tenantId;
}
