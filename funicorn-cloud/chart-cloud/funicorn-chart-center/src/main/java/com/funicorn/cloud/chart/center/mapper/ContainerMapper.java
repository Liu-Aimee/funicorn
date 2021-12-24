package com.funicorn.cloud.chart.center.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.funicorn.cloud.chart.center.dto.ContainerDTO;
import com.funicorn.cloud.chart.center.dto.ContainerPageDTO;
import com.funicorn.cloud.chart.center.entity.Container;
import com.funicorn.cloud.chart.center.vo.ContainerSetVO;
import com.funicorn.cloud.chart.center.vo.ContainerVO;
import org.apache.ibatis.annotations.Param;

/**
 * 容器表映射类
 * @author Aimee
 */
@SuppressWarnings("unused")
public interface ContainerMapper extends BaseMapper<Container> {

    /**
     * 广场容器分页查询
     * @param containerPageDTO 入参
     * @param page 分页信息
     * @return ContainerVO
     * */
    IPage<ContainerVO> selectMarketMage(Page<ContainerVO> page, @Param("containerPageDTO") ContainerPageDTO containerPageDTO);

    /**
     * 管理容器配置分页查询
     * @param containerDTO 入参
     * @param page 分页信息
     * @return IPage<ContainerSetVO>
     * */
    IPage<ContainerSetVO> queryManagePage(Page<ContainerSetVO> page, @Param("containerDTO") ContainerDTO containerDTO);
}
