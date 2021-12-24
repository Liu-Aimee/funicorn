package com.funicorn.cloud.chart.center.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.funicorn.cloud.chart.center.dto.ContainerSetDTO;
import com.funicorn.cloud.chart.center.entity.ContainerData;
import com.funicorn.cloud.chart.center.vo.ContainerSetVO;

/**
 * 容器数据集表业务类
 * @author Aimee
 */
public interface ContainerDataService extends IService<ContainerData> {

    /**
     * 容器配置分页查询
     * @param containerSetDTO 入参
     * @return IPage<ContainerSetVO>
     * */
    IPage<ContainerSetVO> page(ContainerSetDTO containerSetDTO);
}
