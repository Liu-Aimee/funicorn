package com.funicorn.cloud.system.center.dto;

import com.funicorn.basic.common.datasource.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Aimee
 * @since 2021/12/1 14:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RouteQueryPageDTO extends PageDTO {

    /**
     * 服务名称
     * */
    private String name;

    /**
     * 转发地址
     * */
    private String uri;
}
