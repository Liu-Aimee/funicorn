package com.funicorn.cloud.chart.center.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funicorn.cloud.chart.center.constant.ChartConstant;
import com.funicorn.cloud.chart.center.dto.ContainerSetDTO;
import com.funicorn.cloud.chart.center.entity.ContainerData;
import com.funicorn.cloud.chart.center.mapper.ContainerDataMapper;
import com.funicorn.cloud.chart.center.service.ContainerDataService;
import com.funicorn.cloud.chart.center.vo.ContainerSetVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 容器数据集表业务实现类
 * @author Aimee
 */
@Service
public class ContainerDataServiceImpl extends ServiceImpl<ContainerDataMapper, ContainerData> implements ContainerDataService {


    @Override
    public IPage<ContainerSetVO> page(ContainerSetDTO containerSetDTO) {
        Page<ContainerSetVO> page = new Page<>(containerSetDTO.getCurrent(),containerSetDTO.getSize());
        IPage<ContainerSetVO> iPage = baseMapper.queryPage(page,containerSetDTO);
        if (iPage==null || iPage.getRecords()==null || iPage.getRecords().isEmpty()){
            return iPage;
        }

        iPage.getRecords().forEach(containerSetVO -> {
            List<ContainerData> containerDataList = baseMapper.selectList(Wrappers.<ContainerData>lambdaQuery()
                    .eq(ContainerData::getContainerId,containerSetVO.getId())
                    .eq(ContainerData::getIsDelete, ChartConstant.NOT_DELETED));
            containerSetVO.setContainerData(containerDataList);
        });
        return iPage;
    }
}