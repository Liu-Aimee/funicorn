package com.funicorn.cloud.chart.center.vo;

import com.funicorn.cloud.chart.center.entity.Container;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Aimee
 * @since 2021/9/1 10:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ContainerVO extends Container {

    /**
     * 示例图片
     * */
    private String exampleBase64;

    /**
     * 图片url
     * */
    private String coverUrl;

    /**
     * 路由
     * */
    private String router;

    /**
     * example
     * */
    private byte[] example;

}
