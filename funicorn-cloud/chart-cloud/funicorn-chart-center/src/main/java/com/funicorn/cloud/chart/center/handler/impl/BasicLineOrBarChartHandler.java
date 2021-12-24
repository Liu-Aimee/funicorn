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
 * @author Aimme
 * @since 2020/11/19 15:25
 * 基础柱状图/基础曲线图转换器
 */

@Service
public class BasicLineOrBarChartHandler implements ChartHandler {

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

        List<ContainerData> datavContainerDataList = datavContainerDataService.list(Wrappers.<ContainerData>query().lambda().eq(ContainerData::getContainerId,handlerDTO.getContainerId()));

        List<Object> categoryList = new ArrayList<>();
        List<Map<String,Object>> resultList = new ArrayList<>();

        List<Object> legendData = new ArrayList<>();
        datavContainerDataList.forEach(datavContainerDs -> {
            legendData.add(datavContainerDs.getDataAlias());
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
            * 设置X轴坐标分类，如果不为空则无需再次初始化
            * 切记 如果是多个数据集，则x轴的值必须保持一致
            * */
            if (categoryList.isEmpty()){
                List<String> categorySigns = propertySigns.get(ChartConstant.KEY_CATEGORY);
                props.forEach(prop-> prop.forEach((k, v)->{
                    if (categorySigns.contains(k)){
                        categoryList.add(v);
                    }
                }));
            }

            /*
             * 设置Y轴坐标值
             * */
            List<String> valueSigns = propertySigns.get(ChartConstant.KEY_VALUE);
            valueSigns.forEach(valueSign->{
                Map<String,Object> dataMap = new HashMap<>(props.size());
                List<Object> valueList = new ArrayList<>();
                props.forEach(prop-> prop.forEach((k, v)->{
                    if (StringUtils.isNotBlank(valueSign) && StringUtils.isNotBlank(k) && k.equals(valueSign)){
                        valueList.add(v);
                    }
                }));
                dataMap.put("data",valueList);
                dataMap.put("name",datavContainerDs.getDataAlias());
                resultList.add(dataMap);
            });
        });

        chartDataDto.setXzAxis(categoryList);
        chartDataDto.setSeries(resultList);
        chartDataDto.setLegendData(legendData);
        return chartDataDto;
    }
}
