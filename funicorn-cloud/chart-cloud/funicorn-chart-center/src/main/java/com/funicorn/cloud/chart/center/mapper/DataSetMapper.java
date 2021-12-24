package com.funicorn.cloud.chart.center.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.funicorn.cloud.chart.center.dto.DataSetDTO;
import com.funicorn.cloud.chart.center.entity.DataSet;
import com.funicorn.cloud.chart.center.vo.DataSetVO;
import org.apache.ibatis.annotations.Param;

/**
 * 映射类
 * @author Aimee
 */
public interface DataSetMapper extends BaseMapper<DataSet> {

    /**
     * 数据集分页查询
     * @param dataSetDTO 入参
     * @param page 分页信息
     * @return IPage<DataSetVO>
     * */
    IPage<DataSetVO> queryPage(Page<DataSetVO> page, @Param("dataSetDTO") DataSetDTO dataSetDTO);
}
