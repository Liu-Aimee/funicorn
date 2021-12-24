package com.funicorn.cloud.chart.center.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.funicorn.cloud.chart.center.dto.DataSetDTO;
import com.funicorn.cloud.chart.center.entity.DataSet;
import com.funicorn.cloud.chart.center.vo.DataSetVO;

import java.util.List;
import java.util.Map;

/**
 * 业务类
 * @author Aimee
 */
public interface DataSetService extends IService<DataSet> {

    /**
     * 新增数据集
     * @param dataSetDTO 入参
     * @return DataSet
     * */
    DataSet saveDataSet(DataSetDTO dataSetDTO);

    /**
     * 编辑数据集
     * @param dataSetDTO 入参
     * */
    void updateDataSet(DataSetDTO dataSetDTO);

    /**
     * 数据集分页查询
     * @param dataSetDTO 入参
     * @return IPage<DataSetVO>
     * */
    IPage<DataSetVO> getPage(DataSetDTO dataSetDTO);

    /**
     * 获取出参属性集合
     * @param dataSetDTO 入参
     * @return List<ResponseProp>
     * */
    List<Map<String, Object>> getResponseProps(DataSetDTO dataSetDTO);

}
