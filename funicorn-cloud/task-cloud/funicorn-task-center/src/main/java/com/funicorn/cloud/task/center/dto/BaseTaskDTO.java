package com.funicorn.cloud.task.center.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Aimee
 * @since 2021/12/10 9:47
 */
@Data
public class BaseTaskDTO {

    /**
     * 任务id
     * */
    private String taskId;

    /**
     * 流程参数
     * */
    private Map<String, Object> variables = new HashMap<>();

    /**
     * 处理意见
     * */
    private String message;
}
