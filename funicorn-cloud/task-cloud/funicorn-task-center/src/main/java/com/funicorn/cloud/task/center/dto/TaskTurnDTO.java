package com.funicorn.cloud.task.center.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Aimee
 * @since 2021/12/14 16:20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TaskTurnDTO extends BaseTaskDTO{

    /**
     * 转办人/委派人账号
     * */
    private String turnUsername;
}
