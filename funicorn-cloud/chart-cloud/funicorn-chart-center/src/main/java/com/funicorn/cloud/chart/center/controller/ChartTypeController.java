package com.funicorn.cloud.chart.center.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.funicorn.basic.common.base.model.Result;
import com.funicorn.basic.common.base.util.JsonUtil;
import com.funicorn.basic.common.datasource.util.ConvertUtil;
import com.funicorn.cloud.chart.center.constant.ChartConstant;
import com.funicorn.cloud.chart.center.dto.ChartTypeDTO;
import com.funicorn.cloud.chart.center.entity.ChartType;
import com.funicorn.cloud.chart.center.service.ChartTypeService;
import com.funicorn.cloud.chart.center.vo.ChartTypeVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 图表类型管理
 * @author Aimee
 * @since 2021/12/03
 */
@RestController
@RequestMapping("/ChartType")
public class ChartTypeController {

    @Resource
    private ChartTypeService chartTypeService;

    /**
     * 新增图表类型
     * @return Result
     */
    @PostMapping("/add")
    public Result<ChartType> addChartType(@RequestBody ChartTypeDTO chartTypeDTO) {
        ChartType chartType = chartTypeService.addChartType(chartTypeDTO);
        return Result.ok(chartType);
    }

    /**
     * 查询所有图表类型
     * */
    @GetMapping("/list")
    public Result<List<ChartTypeVO>> list(){
        List<ChartType> chartTypeList = chartTypeService.list(Wrappers.<ChartType>lambdaQuery().eq(ChartType::getIsDelete, ChartConstant.NOT_DELETED));
        return Result.ok(ConvertUtil.list2List(chartTypeList,ChartTypeVO.class));
    }

    /**
     * 分页查询
     * @return Result
     */
    @GetMapping("/page")
    public Result<IPage<ChartTypeVO>> page(Page<ChartType> page, ChartTypeDTO chartTypeDTO) {
        LambdaQueryWrapper<ChartType> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(chartTypeDTO.getName())){
            lambdaQueryWrapper.like(ChartType::getName,chartTypeDTO.getName());
        }
        if (StringUtils.isNotBlank(chartTypeDTO.getCategoryId())){
            lambdaQueryWrapper.eq(ChartType::getCategoryId,chartTypeDTO.getCategoryId());
        }
        lambdaQueryWrapper.eq(ChartType::getIsDelete, ChartConstant.NOT_DELETED);
        lambdaQueryWrapper.orderByDesc(ChartType::getCreatedTime);
        IPage<ChartType> iPage = chartTypeService.page(page,lambdaQueryWrapper);
        return Result.ok(ConvertUtil.page2Page(iPage,ChartTypeVO.class));
    }

    /**
     * 修改图表类型
     * @return Result
     */
    @PostMapping("/update")
    public Result<ChartType> update(@RequestBody ChartTypeDTO chartTypeDTO) {
        ChartType chartType = JsonUtil.object2Object(chartTypeDTO,ChartType.class);
        chartTypeService.updateById(chartType);
        return Result.ok(chartType);
    }

}
