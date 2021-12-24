package com.funicorn.cloud.chart.center.dto;

import com.funicorn.cloud.chart.center.entity.RequestParam;
import com.funicorn.cloud.chart.center.entity.ResponseProp;
import lombok.Data;

import java.util.List;

/**
 * @author Aimee
 * @since 2021/9/3 14:52
 */
@Data
public class FunctionSqlDTO {

    /**主键id*/
    private String id;

    /**函数名称*/
    private String name;

    /**数据源id*/
    private String datasourceId;

    /**sql语句*/
    private String sqlContent;

    /**第几页*/
    private Integer current;

    /**每页数量*/
    private Integer size;

    /**创建人*/
    private String createBy;

    /**入参*/
    private List<RequestParam> params;

    /**出参*/
    private List<ResponseProp> props;
}
