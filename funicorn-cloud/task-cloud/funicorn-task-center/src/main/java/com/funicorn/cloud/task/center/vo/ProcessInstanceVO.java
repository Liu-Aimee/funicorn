package com.funicorn.cloud.task.center.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Aimee
 * @since 2021/12/13 16:01
 */
@Data
public class ProcessInstanceVO {

    private String id;

    private String name;

    private String processDefinitionKey;

    private String processDefinitionName;

    private Integer processDefinitionVersion;

    private String deploymentId;

    private Integer revision;

    private String processInstanceId;

    private String processDefinitionId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String startUserId;

    private String startActivityId;

    private String tenantId;
}
