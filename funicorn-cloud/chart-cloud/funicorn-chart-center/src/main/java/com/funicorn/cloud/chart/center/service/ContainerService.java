package com.funicorn.cloud.chart.center.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.funicorn.cloud.chart.center.dto.ContainerDTO;
import com.funicorn.cloud.chart.center.dto.ContainerPageDTO;
import com.funicorn.cloud.chart.center.entity.Container;
import com.funicorn.cloud.chart.center.vo.ContainerSetVO;
import com.funicorn.cloud.chart.center.vo.ContainerVO;

/**
 * 容器表业务类
 * @author Aimee
 */
public interface ContainerService extends IService<Container> {

    /**
     * 图表广场分页查询
     * @param containerPageDTO 入参
     * @return ContainerVO
     * */
    IPage<ContainerVO> marketPage(ContainerPageDTO containerPageDTO);

    /**
     * 容器管理分页查询
     * @param containerDTO 入参
     * @return IPage<ContainerSetVO>
     * */
    IPage<ContainerSetVO> managePage(ContainerDTO containerDTO);

    /**
     * 新增容器
     * @param containerDTO 入参
     * @return ContainerSetVO
     * */
    ContainerSetVO addContainer(ContainerDTO containerDTO);

    /**
     * 修改容器
     * @param containerDTO 入参
     * @return ContainerSetVO
     * */
    ContainerSetVO updateContainer(ContainerDTO containerDTO);
}
