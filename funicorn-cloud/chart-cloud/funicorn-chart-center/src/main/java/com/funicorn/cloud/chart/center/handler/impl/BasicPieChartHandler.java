package com.funicorn.cloud.chart.center.handler.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.funicorn.basic.common.base.util.JsonUtil;
import com.funicorn.cloud.chart.center.constant.ChartConstant;
import com.funicorn.cloud.chart.center.dto.ChartDataDTO;
import com.funicorn.cloud.chart.center.dto.DataSetDTO;
import com.funicorn.cloud.chart.center.dto.HandlerDTO;
import com.funicorn.cloud.chart.center.entity.Container;
import com.funicorn.cloud.chart.center.entity.ContainerData;
import com.funicorn.cloud.chart.center.entity.DataSet;
import com.funicorn.cloud.chart.center.exception.DatavException;
import com.funicorn.cloud.chart.center.exception.ErrorCode;
import com.funicorn.cloud.chart.center.handler.ChartHandler;
import com.funicorn.cloud.chart.center.service.ContainerDataService;
import com.funicorn.cloud.chart.center.service.ContainerService;
import com.funicorn.cloud.chart.center.service.DataSetService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基础饼图渲染处理器
 * @author Aimme
 * @since 2020/11/23 15:31
 */
@Service
public class BasicPieChartHandler implements ChartHandler {

    @Resource
    private ContainerService datavContainerService;
    @Resource
    private ContainerDataService datavContainerDataService;
    @Resource
    private DataSetService dataSetService;

    @SuppressWarnings("unchecked")
    @Override
    public ChartDataDTO invoke(HandlerDTO handlerDTO) {

        ChartDataDTO chartDataDto = new ChartDataDTO();
        Container datavContainer = datavContainerService.getById(handlerDTO.getContainerId());
        /*
         * 图表名称
         * */
        chartDataDto.setTitleText(datavContainer.getName());
        List<Map<String,Object>> resultList = new ArrayList<>();
        List<Object> legendData = new ArrayList<>();
        List<ContainerData> datavContainerDataList = datavContainerDataService.list(Wrappers.<ContainerData>query().lambda().eq(ContainerData::getContainerId,handlerDTO.getContainerId()));
        datavContainerDataList.forEach(datavContainerDs -> {
            DataSet dataSet = dataSetService.getById(datavContainerDs.getDataId());

            if (dataSet==null) {
                throw new DatavException(ErrorCode.NOT_FOUND_DATA_SET,datavContainerDs.getDataId());
            }

            DataSetDTO dataSetDTO = JsonUtil.object2Object(dataSet,DataSetDTO.class);
            dataSetDTO.setParams(handlerDTO.getParams());
            List<Map<String,Object>> props = dataSetService.getResponseProps(dataSetDTO);

            if (StringUtils.isBlank(datavContainerDs.getDataPropertySigns())){
                throw new DatavException(ErrorCode.NOT_SET_SIGN);
            }

            Map<String,List<String>> propertySigns = JsonUtil.json2Object(datavContainerDs.getDataPropertySigns(),Map.class);

            /*
             * 设置饼图分类名称，如果不为空则无需再次初始化
             * 饼图一个数据集应该只有一个category_key 和 value_key
             * */
            String category = propertySigns.get(ChartConstant.KEY_CATEGORY).get(0);
            if (legendData.isEmpty()){
                props.forEach(prop-> prop.forEach((k, v)->{
                    if (category.equals(k)){
                        legendData.add(v);
                    }
                }));
            }

            String value = propertySigns.get(ChartConstant.KEY_VALUE).get(0);
            props.forEach(prop->{
                Map<String,Object> dataMap = new HashMap<>(2);
                dataMap.put("name",prop.get(category));
                dataMap.put("value",prop.get(value));
                resultList.add(dataMap);
            });
        });

        chartDataDto.setLegendData(legendData);
        chartDataDto.setSeries(resultList);
        return chartDataDto;
    }
}
