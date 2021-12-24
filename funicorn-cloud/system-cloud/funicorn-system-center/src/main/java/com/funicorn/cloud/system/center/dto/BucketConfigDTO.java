package com.funicorn.cloud.system.center.dto;

import lombok.Data;

/**
 * @author Aimee
 * @since 2021/11/15 9:23
 */
@Data
public class BucketConfigDTO {

    /**
     * 桶名称
     * */
    private String bucketName;

    /**
     * 是否开启加速 true/false
     * */
    private Boolean versionStatus;

    /**
     * 权限 public/private
     * */
    private String acl;
}
