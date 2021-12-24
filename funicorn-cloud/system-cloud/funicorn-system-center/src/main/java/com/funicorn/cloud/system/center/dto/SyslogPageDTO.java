package com.funicorn.cloud.system.center.dto;

import com.funicorn.basic.common.datasource.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
     * 日志类型
     * */
    private String logType;

    /**
     * 用户名
     * */
    private String username;

    /**
     * 租户id
     * */
    private String tenantId;
}
