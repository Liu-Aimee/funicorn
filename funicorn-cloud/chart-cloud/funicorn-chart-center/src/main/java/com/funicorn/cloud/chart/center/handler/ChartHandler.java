package com.funicorn.cloud.chart.center.handler;


import com.funicorn.cloud.chart.center.dto.ChartDataDTO;
import com.funicorn.cloud.chart.center.dto.HandlerDTO;

/**
 * @author Aimme
 * @since 2020/11/19 15:06
 *
 * 图表处理器接口
 */
public interface ChartHandler {

    /**
    * 图表数据转换函数
    * @param handlerDTO 入参
    * @return ChartDataDto
    * */
    ChartDataDTO invoke(HandlerDTO handlerDTO);

}
