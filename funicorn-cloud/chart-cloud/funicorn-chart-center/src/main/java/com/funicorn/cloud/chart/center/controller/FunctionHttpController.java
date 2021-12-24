package com.funicorn.cloud.chart.center.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.funicorn.basic.common.base.model.Result;
import com.funicorn.cloud.chart.center.dto.FunctionHttpDTO;
import com.funicorn.cloud.chart.center.service.FunctionHttpService;
import com.funicorn.cloud.chart.center.vo.FunctionHttpVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Http类型数据管理
 * @author Aimee
 * @since 2021/12/03
 */
@RestController
@RequestMapping("/FunctionHttp")
public class FunctionHttpController {

    @Resource
    private FunctionHttpService functionHttpService;

    /**
     * 分页查询
     * @return Result
     */
    @GetMapping("/page")
    public Result<IPage<FunctionHttpVO>> page(FunctionHttpDTO functionHttpDTO){
        return Result.ok(functionHttpService.page(functionHttpDTO));
    }

    /**
     * 接口测试
     * @return Result
     */
    @PostMapping("/testHttp")
    public Result<List<Map<String, Object>>> testHttp(@RequestBody FunctionHttpDTO functionHttpDTO) {
        return Result.ok(functionHttpService.testHttp(functionHttpDTO));
    }

    /**
     * 新增http函数
     * @return Result
     */
    @PostMapping("/add")
    public Result<?> add(@RequestBody FunctionHttpDTO functionHttpDTO) {
        functionHttpService.addFunctionHttp(functionHttpDTO);
        return Result.ok();
    }

    /**
     * 修改http函数
     * @return Result
     */
    @PostMapping("/update")
    public Result<?> update(@RequestBody FunctionHttpDTO functionHttpDTO) {
        functionHttpService.updateFunctionHttp(functionHttpDTO);
        return Result.ok();
    }
}

