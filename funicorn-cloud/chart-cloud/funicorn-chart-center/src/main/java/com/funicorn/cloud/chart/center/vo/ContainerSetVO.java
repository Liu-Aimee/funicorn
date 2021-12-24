package com.funicorn.cloud.chart.center.vo;

import com.funicorn.cloud.chart.center.entity.Container;
import com.funicorn.cloud.chart.center.entity.ContainerData;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Aimee
 * @since 2021/9/6 16:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ContainerSetVO extends Container {

    /**
     * 渲染路由
     * */
    private String router;

    /**
     * 数据集数组
     * */
    private List<ContainerData> containerData;
}
