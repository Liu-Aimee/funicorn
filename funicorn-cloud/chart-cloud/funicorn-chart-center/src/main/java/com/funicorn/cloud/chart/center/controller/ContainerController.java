package com.funicorn.cloud.chart.center.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.funicorn.basic.common.base.model.Result;
import com.funicorn.basic.common.base.util.AppContextUtil;
import com.funicorn.cloud.chart.center.dto.ChartDataDTO;
import com.funicorn.cloud.chart.center.dto.ContainerDTO;
import com.funicorn.cloud.chart.center.dto.ContainerPageDTO;
import com.funicorn.cloud.chart.center.dto.HandlerDTO;
import com.funicorn.cloud.chart.center.entity.ChartType;
import com.funicorn.cloud.chart.center.entity.Container;
import com.funicorn.cloud.chart.center.handler.ChartHandler;
import com.funicorn.cloud.chart.center.service.ChartTypeService;
import com.funicorn.cloud.chart.center.service.ContainerService;
import com.funicorn.cloud.chart.center.vo.ContainerSetVO;
import com.funicorn.cloud.chart.center.vo.ContainerVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 容器管理
 * @author Aimee
 * @since 2021/12/03
 */
@RestController
@RequestMapping("/Container")
public class ContainerController {

    @Resource
    private ContainerService containerService;
    @Resource
    private ChartTypeService chartTypeService;

    /**
     * 图表广场分页查询
     * @param containerPageDTO 入参
     * @return Result
     */
    @GetMapping("/market")
    public Result<IPage<ContainerVO>> market(ContainerPageDTO containerPageDTO) {
        return Result.ok(containerService.marketPage(containerPageDTO));
    }

    /**
     * 容器管理分页查询
     * @return Result
     */
    @GetMapping("/managePage")
    public Result<IPage<ContainerSetVO>> managePage(ContainerDTO containerDTO){
        return Result.ok(containerService.managePage(containerDTO));
    }



    /**
     * 视图渲染
     * @return Result
     */
    @GetMapping("/show")
    public Result<ChartDataDTO> show(HandlerDTO handlerDTO) {
        Container datavContainer = containerService.getById(handlerDTO.getContainerId());
        ChartType datavChartType = chartTypeService.getById(datavContainer.getTypeId());
        ChartHandler chartHandler = (ChartHandler) AppContextUtil.getBean(datavChartType.getBeanType());
        ChartDataDTO chartDataDto = chartHandler.invoke(handlerDTO);
        return Result.ok(chartDataDto);
    }

    /**
     * 新增容器
     * @return Result
     */
    @PostMapping("/add")
    public Result<ContainerSetVO> add(@RequestBody ContainerDTO containerDTO) {
        return Result.ok( containerService.addContainer(containerDTO));
    }

    /**
     * 修改容器
     * @return Result
     */
    @PostMapping("/update")
    public Result<ContainerSetVO> update(@RequestBody ContainerDTO containerDTO) {
        return Result.ok(containerService.updateContainer(containerDTO));
    }
}
