package com.funicorn.cloud.task.center.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Aimee
 * @since 2021/12/13 14:25
 */
@Data
public class ModelVO {

    /**
     * 主键id
     * */
    private String id;

    /**
     * 流程模型名称
     * */
    private String name;

    /**
     * 流程模型key
     * */
    private String modelKey;

    /**
     * 流程模型描述
     * */
    private String description;

    /**
     * 备注
     * */
    private String modelComment;

    /**
     * 创建时间
     * */
    private LocalDateTime created;

    /**
     * 创建人
     * */
    private String createdBy;

    /**
     * 最后更新时间
     * */
    private LocalDateTime lastUpdated;

    /**
     * 最后更新人
     * */
    private String lastUpdatedBy;

    /**
     * 版本号
     * */
    private Integer version;

    /**
     * 流程引擎类型
     * */
    private Integer modelType;

    /**
     * 租户id
     * */
    private String tenantId;
}
