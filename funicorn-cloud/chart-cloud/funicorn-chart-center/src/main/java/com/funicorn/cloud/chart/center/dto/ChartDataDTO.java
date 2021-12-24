package com.funicorn.cloud.chart.center.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author Aimme
 * @since 2020/11/19 15:26
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChartDataDTO {

    /**
    * 图表标题
    * */
    private String titleText;

    /**
    * X轴数据集
    * */
    private List<Object> xzAxis;

    /**
     * 饼图描述
     * */
    private List<Object> legendData;

    /**
     * Y轴数据类型
     * */
    private String yzAxis = "value";

    /**
    * Y轴数据集
    * */
    private List<Map<String,Object>> series;
}
