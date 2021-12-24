package com.funicorn.cloud.task.center.dto;

import lombok.Data;

/**
 * @author Aimme
 * @date 2020/12/22 17:13
 */
@Data
public class BaseProcessDTO {

    /**
     * 任务id
     */
    private String taskId;
    /**
     * 审批意见
     */
    private String message;
    /**
     * 流程实例的id 必填
     */
    private String processInstanceId;
    /**
     * 审批类型
     */
    private String type;
}
