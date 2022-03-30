package com.funicorn.cloud.system.center.dto;

import com.funicorn.basic.common.datasource.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Aimee
 * @since 2021/11/29 10:53
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SyslogPageDTO extends PageDTO {

    /**
     * 操作类型
     * */
    private String operationType;

    /**
     * 用户名
     * */
    private String username;

    /**
     * 租户id
     * */
    private String tenantId;

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

    /**
     * 应用名称
     * */
    private String serviceName;
}
