package com.funicorn.cloud.task.center.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Aimee
 * @since 2021/12/13 16:10
 */
@Data
public class TaskVO {

    private String id;

    private String processInstanceName;

    private String parentTaskId;

    private String name;

    private String executionId;

    private String processInstanceId;

    private String processDefinitionId;

    private String taskDefinitionKey;

    private String tenantId;

    private String owner;

    private LocalDateTime createTime;

    private LocalDateTime endTime;

    private String assignee;
}
