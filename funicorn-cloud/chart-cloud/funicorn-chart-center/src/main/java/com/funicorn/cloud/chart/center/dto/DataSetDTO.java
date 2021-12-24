package com.funicorn.cloud.chart.center.dto;

import com.funicorn.basic.common.datasource.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Aimme
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class DataSetDTO extends PageDTO {

    /**主键id*/
    private String id;

    /**创建人*/
    private String createBy;

    /**函数id*/
    private String functionId;

    /**数据集名称*/
    private String name;

    /**
     * 数据集类型
     * */
    private String type;

    /**
     * 项目id
     * */
    private String projectId;

    /**
     * 入参
     * */
    private String params;
}
