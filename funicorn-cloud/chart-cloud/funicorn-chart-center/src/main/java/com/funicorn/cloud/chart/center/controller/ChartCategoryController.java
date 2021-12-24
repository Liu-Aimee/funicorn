package com.funicorn.cloud.chart.center.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.funicorn.basic.common.base.model.Result;
import com.funicorn.cloud.chart.center.constant.ChartConstant;
import com.funicorn.cloud.chart.center.entity.ChartCategory;
import com.funicorn.cloud.chart.center.exception.DatavException;
import com.funicorn.cloud.chart.center.exception.ErrorCode;
import com.funicorn.cloud.chart.center.service.ChartCategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 图表分类管理
 * @author Aimee
 * @since 2021/12/03
 */
@RestController
@RequestMapping("/ChartCategory")
public class ChartCategoryController {

    @Resource
    private ChartCategoryService chartCategoryService;

    /**
     * 查询所有图表分类
     * */
    @GetMapping("/list")
    public Result<List<ChartCategory>> list(){
        List<ChartCategory> list = chartCategoryService.list(Wrappers.<ChartCategory>lambdaQuery()
                .eq(ChartCategory::getIsDelete, ChartConstant.NOT_DELETED));
        return Result.ok(list);
    }

    /**
     * 新增分类
     * @return Result
     */
    @PostMapping("/add")
    public Result<ChartCategory> save(@RequestBody ChartCategory datavChartCategory) {
        int count = chartCategoryService.count(Wrappers.<ChartCategory>lambdaQuery()
                .eq(ChartCategory::getName,datavChartCategory.getName())
                .eq(ChartCategory::getIsDelete, ChartConstant.NOT_DELETED));
        if (count>0){
            throw new DatavException(ErrorCode.CHART_TYPE_IS_EXISTS,datavChartCategory.getName());
        }
        chartCategoryService.save(datavChartCategory);
        return Result.ok(datavChartCategory);
    }

    /**
     * 修改分类
     * @return Result
     */
    @PostMapping("/update")
    public Result<ChartCategory> updateById(@RequestBody ChartCategory datavChartCategory) {
        int count = chartCategoryService.count(Wrappers.<ChartCategory>lambdaQuery()
                .eq(ChartCategory::getName,datavChartCategory.getName())
                .eq(ChartCategory::getIsDelete, ChartConstant.NOT_DELETED)
                .ne(ChartCategory::getId,datavChartCategory.getId()));
        if (count>0){
            throw new DatavException(ErrorCode.CHART_TYPE_IS_EXISTS,datavChartCategory.getName());
        }
        chartCategoryService.updateById(datavChartCategory);
        return Result.ok(datavChartCategory);
    }

}
