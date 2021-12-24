package com.funicorn.cloud.chart.center.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funicorn.basic.common.base.util.JsonUtil;
import com.funicorn.cloud.chart.center.constant.ChartConstant;
import com.funicorn.cloud.chart.center.dto.ContainerDTO;
import com.funicorn.cloud.chart.center.dto.ContainerPageDTO;
import com.funicorn.cloud.chart.center.entity.Container;
import com.funicorn.cloud.chart.center.entity.ContainerData;
import com.funicorn.cloud.chart.center.mapper.ContainerMapper;
import com.funicorn.cloud.chart.center.service.ContainerDataService;
import com.funicorn.cloud.chart.center.service.ContainerService;
import com.funicorn.cloud.chart.center.vo.ContainerSetVO;
import com.funicorn.cloud.chart.center.vo.ContainerVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 容器表业务实现类
 * @author Aimee
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ContainerServiceImpl extends ServiceImpl<ContainerMapper, Container> implements ContainerService {

    @Resource
    private ContainerDataService containerDataService;

    @Override
    public IPage<ContainerVO> marketPage(ContainerPageDTO containerPageDTO) {
        IPage<ContainerVO> iPage = baseMapper.selectMarketMage(new Page<>(containerPageDTO.getCurrent(),containerPageDTO.getSize()),containerPageDTO);
        if (iPage==null || iPage.getRecords()==null || iPage.getRecords().isEmpty()){
            return iPage;
        }
        //TODO 实例图片改为预加载 或者 调用文件管理图片url
        iPage.getRecords().forEach(containerVO -> {
            if (containerVO.getExample()!=null){
                containerVO.setExampleBase64(new String(containerVO.getExample(), StandardCharsets.UTF_8));
            }
        });
        return iPage;
    }

    @Override
    public IPage<ContainerSetVO> managePage(ContainerDTO containerDTO) {
        Page<ContainerSetVO> page = new Page<>(containerDTO.getCurrent(),containerDTO.getSize());
        IPage<ContainerSetVO> iPage = baseMapper.queryManagePage(page,containerDTO);
        if (iPage==null || iPage.getRecords()==null || iPage.getRecords().isEmpty()){
            return iPage;
        }

        iPage.getRecords().forEach(containerSetVO -> {
            List<ContainerData> containerDataList = containerDataService.list(Wrappers.<ContainerData>lambdaQuery()
                    .eq(ContainerData::getContainerId,containerSetVO.getId())
                    .eq(ContainerData::getIsDelete, ChartConstant.NOT_DELETED));
            containerSetVO.setContainerData(containerDataList);
        });
        return iPage;
    }

    @Override
    public ContainerSetVO addContainer(ContainerDTO containerDTO) {
        // 1、保存容器
        Container container = JsonUtil.object2Object(containerDTO,Container.class);
        baseMapper.insert(container);
        // 2、保存容器数据
        List<ContainerData> containerDataList = new ArrayList<>();
        if (containerDTO.getContainerData()!=null && !containerDTO.getContainerData().isEmpty()){
            containerDTO.getContainerData().forEach(containerData -> {
                containerData.setContainerId(container.getId());
                containerDataList.add(containerData);
            });
            containerDataService.saveBatch(containerDataList);
        }

        ContainerSetVO containerSetVO = JsonUtil.object2Object(container,ContainerSetVO.class);
        containerSetVO.setContainerData(containerDataList);
        return containerSetVO;
    }

    @Override
    public ContainerSetVO updateContainer(ContainerDTO containerDTO) {
        // 1、修改容器
        Container container = JsonUtil.object2Object(containerDTO,Container.class);
        baseMapper.updateById(container);
        // 2、保存容器数据
        containerDataService.remove(Wrappers.<ContainerData>lambdaQuery().eq(ContainerData::getContainerId,container.getId()));
        List<ContainerData> containerDataList = new ArrayList<>();
        if (containerDTO.getContainerData()!=null && !containerDTO.getContainerData().isEmpty()){
            containerDTO.getContainerData().forEach(containerData -> {
                containerData.setContainerId(container.getId());
                containerDataList.add(containerData);
            });
            containerDataService.saveBatch(containerDataList);
        }

        ContainerSetVO containerSetVO = JsonUtil.object2Object(container,ContainerSetVO.class);
        containerSetVO.setContainerData(containerDataList);
        return containerSetVO;
    }
}