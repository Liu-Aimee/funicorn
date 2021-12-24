package com.funicorn.cloud.task.center.dto;

import lombok.Data;

/**
 * @author Aimee
 * @since 2021/12/14 15:47
 */
@Data
public class ImageDTO {

    /**
     * 流程定义id
     * */
    private String processDefinitionId;

    /**
     * 流程实例id
     * */
    private String processInstanceId;

    /**
     * 流程定义key
     * */
    private String processDefinitionKey;
}
