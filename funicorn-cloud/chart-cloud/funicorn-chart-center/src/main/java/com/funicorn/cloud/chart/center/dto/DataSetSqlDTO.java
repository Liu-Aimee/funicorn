package com.funicorn.cloud.chart.center.dto;

import lombok.Data;

/**
 * @author Aimme
 * @since 2020/11/16 11:28
 */
@Data
public class DataSetSqlDTO {

    /**
    * 数据源id
    * */
    private String datasourceId;

    /**
     * 数据集id
     * */
    private String setId;

    /**
     * sql语句
     * */
    private String sqlContent;
}
