package com.funicorn.cloud.chart.center.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.funicorn.cloud.chart.center.dto.ContainerSetDTO;
import com.funicorn.cloud.chart.center.entity.ContainerData;
import com.funicorn.cloud.chart.center.vo.ContainerSetVO;
import org.apache.ibatis.annotations.Param;

/**
 * 容器数据集表映射类
 * @author Aimee
 */
public interface ContainerDataMapper extends BaseMapper<ContainerData> {

    /**
     * 容器配置分页查询
     * @param containerSetDTO 入参
     * @param page 分页信息
     * @return IPage<ContainerSetVO>
     * */
    IPage<ContainerSetVO> queryPage(Page<ContainerSetVO> page, @Param("containerSetDTO") ContainerSetDTO containerSetDTO);
}
