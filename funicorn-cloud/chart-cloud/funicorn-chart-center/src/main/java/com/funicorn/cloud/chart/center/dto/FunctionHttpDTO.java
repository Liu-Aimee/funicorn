package com.funicorn.cloud.chart.center.dto;

import com.funicorn.basic.common.datasource.dto.PageDTO;
import com.funicorn.cloud.chart.center.entity.RequestParam;
import com.funicorn.cloud.chart.center.entity.ResponseProp;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Aimee
 * @since 2021/9/6 14:53
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FunctionHttpDTO extends PageDTO {

    /**创建人*/
    private String createdBy;

    /**主键id*/
    private String id;

    /**函数名称*/
    private String name;

    /**请求类型 GET/POST*/
    private String method;

    /**请求地址*/
    private String url;

    /**请求超时时间*/
    private Integer connectTimeOut;

    /**读取超时时间*/
    private Integer readTimeOut;

    /**入参*/
    private List<RequestParam> params;

    /**出参*/
    private List<ResponseProp> props;
}
