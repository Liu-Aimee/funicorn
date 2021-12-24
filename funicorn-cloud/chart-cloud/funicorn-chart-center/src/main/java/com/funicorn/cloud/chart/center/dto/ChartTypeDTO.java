package com.funicorn.cloud.chart.center.dto;

import lombok.Data;

/**
 * @author Aimee
 * @since 2021/8/28 11:18
 */
@Data
public class ChartTypeDTO {

    /**
     * 主键id
     * */
    private String id;

    /**类型名称*/
    private String name;

    /**分类id*/
    private String categoryId;

    /**类型对应bean名称*/
    private String beanType;

    /**前端路由*/
    private String router;

    /**示例图片*/
    private String exampleBase64;

    /**示例图片路径*/
    private String exampleUrl;
}
