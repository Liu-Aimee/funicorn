package com.funicorn.cloud.chart.center.controller;

import com.funicorn.basic.common.base.model.Result;
import com.funicorn.cloud.chart.center.service.RequestParamService;
import org.springframework.web.bind.annotation.*;
import com.funicorn.cloud.chart.center.entity.RequestParam;

import javax.annotation.Resource;

/**
 * 入参管理
 * @author Aimee
 * @since 2021/12/03
 */
@RestController
@RequestMapping("/DataSetParam")
public class DataSetParamController {

    @Resource
    private RequestParamService requestParamService;

    /**
     * 新增函数入参
     * @return Result
     */
    @PostMapping("/add")
    public Result<?> save(@RequestBody RequestParam datavRequestParam) {
        return Result.ok( requestParamService.save(datavRequestParam));
    }

    /**
     * 修改函数入参
     * @return Result
     */
    @PatchMapping("/update")
    public Result<?> updateById(@RequestBody RequestParam datavRequestParam) {
        requestParamService.updateById(datavRequestParam);
        return Result.ok();
    }
}
