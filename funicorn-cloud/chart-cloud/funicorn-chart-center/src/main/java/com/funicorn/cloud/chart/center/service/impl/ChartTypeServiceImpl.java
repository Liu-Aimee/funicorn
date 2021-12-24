package com.funicorn.cloud.chart.center.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funicorn.basic.common.base.util.JsonUtil;
import com.funicorn.cloud.chart.center.dto.ChartTypeDTO;
import com.funicorn.cloud.chart.center.entity.ChartType;
import com.funicorn.cloud.chart.center.mapper.ChartTypeMapper;
import com.funicorn.cloud.chart.center.service.ChartTypeService;
import org.springframework.stereotype.Service;

/**
 * 图表类型表业务实现类
 * @author Aimee
 */
@Service
public class ChartTypeServiceImpl extends ServiceImpl<ChartTypeMapper, ChartType> implements ChartTypeService {

    @Override
    public ChartType addChartType(ChartTypeDTO chartTypeDTO) {
        ChartType chartType = JsonUtil.object2Object(chartTypeDTO,ChartType.class);
        baseMapper.insert(chartType);
        return chartType;
    }
}