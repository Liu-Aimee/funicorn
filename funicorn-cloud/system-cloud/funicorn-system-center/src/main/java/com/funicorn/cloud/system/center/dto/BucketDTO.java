package com.funicorn.cloud.system.center.dto;

import lombok.Data;

/**
 * @author Aimee
 * @since 2022/3/18 17:11
 */
@Data
public class BucketDTO {

    /**
     * 桶名
     * */
    private String name;

    /**
     * 级别
     * */
    private String level;

    /**
     * 租户id
     * */
    private String tenantId;
}
