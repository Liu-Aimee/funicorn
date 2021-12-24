package com.funicorn.cloud.chart.center.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.funicorn.cloud.chart.center.dto.ChartTypeDTO;
import com.funicorn.cloud.chart.center.entity.ChartType;

/**
 * 图表类型表业务类
 * @author Aimee
 */
public interface ChartTypeService extends IService<ChartType> {

    /**
     * 新增图片类型
     * @param chartTypeDTO 入参
     * @return ChartType
     * */
    ChartType addChartType(ChartTypeDTO chartTypeDTO);
}
