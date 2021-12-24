package com.funicorn.cloud.chart.center.vo;

import lombok.Data;

/**
 * @author Aimee
 * @since 2021/9/6 17:13
 */
@Data
public class ChartTypeVO {

    /**
     * 主键id
     * */
    private String id;

    /**
     * 类型名称
     * */
    private String name;

    /**
     * 大类型id
     * */
    private String categoryId;

    /**
     * 类型对应bean名称
     * */
    private String beanType;

    /**
     * 前端路由
     * */
    private String router;

    /**
     * 示例图片路径
     * */
    private String exampleUrl;
}
