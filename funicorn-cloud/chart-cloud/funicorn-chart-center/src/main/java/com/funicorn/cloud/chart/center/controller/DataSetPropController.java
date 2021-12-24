package com.funicorn.cloud.chart.center.controller;

import com.funicorn.basic.common.base.model.Result;
import com.funicorn.cloud.chart.center.entity.ResponseProp;
import com.funicorn.cloud.chart.center.service.ResponsePropService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 出参管理
 * @author Aimee
 * @since 2021/12/03
 */
@RestController
@AllArgsConstructor
@RequestMapping("/DataSetProp")
public class DataSetPropController {

    @Resource
    private final ResponsePropService responsePropService;

    /**
     * 新增出参
     * @return Result
     */
    @PostMapping("/add")
    public Result<?> save(@RequestBody ResponseProp datavResponseProp) {
        return Result.ok( responsePropService.save(datavResponseProp));
    }

    /**
     * 修改出参
     * @return Result
     */
    @PatchMapping("/update")
    public Result<?> updateById(@RequestBody ResponseProp datavResponseProp) {
        responsePropService.updateById(datavResponseProp);
        return Result.ok();
    }
}
