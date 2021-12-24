package com.funicorn.cloud.task.center.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Aimee
 * @since 2021/12/10 9:57
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TaskCompleteDTO extends BaseTaskDTO{

    /**
     * 审批意见
     * */
    private String message;
}
