package com.funicorn.cloud.task.center.dto;

import com.funicorn.basic.common.datasource.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Aimee
 * @since 2021/12/9 17:16
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TaskPageDTO extends PageDTO {

    /**
     * 任务名称
     * */
    private String taskName;

    /**
     * 待办人
     * */
    private String assignee;

    /**
     * 开始时间
     * */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 结束时间
     * */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
