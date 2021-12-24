package com.funicorn.cloud.chart.center.dto;

import lombok.Data;

/**
 * @author Aimme
 * @since 2020/11/20 9:47
 */
@Data
public class DataSetPropDTO {

    /**
    * 属性名
    * */
    private String name;

    /**
     * 类别名称
     * */
    private String categoryName;

    /**
     * 属性类型
     * */
    private String type;

    /**
     * 展示名称
     * */
    private String label;
}
