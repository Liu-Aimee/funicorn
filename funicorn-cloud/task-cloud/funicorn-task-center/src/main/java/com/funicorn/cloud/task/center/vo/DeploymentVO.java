package com.funicorn.cloud.task.center.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Aimee
 * @since 2021/12/13 14:45
 */
@Data
public class DeploymentVO {

    private String id;

    private String name;

    private String category;

    private String key;

    private String tenantId;

    private LocalDateTime deploymentTime;

    private String parentDeploymentId;
}
