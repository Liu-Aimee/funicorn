package com.funicorn.cloud.chart.center.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.funicorn.basic.common.base.model.Result;
import com.funicorn.cloud.chart.center.dto.FunctionSqlDTO;
import com.funicorn.cloud.chart.center.service.FunctionSqlService;
import com.funicorn.cloud.chart.center.vo.FunctionSqlVO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * sql类型数据管理
 * @author Aimee
 * @since 2021/12/03
 */
@RestController
@AllArgsConstructor
@RequestMapping("/FunctionSql")
public class FunctionSqlController {

    @Resource
    private final FunctionSqlService functionSqlService;

    /**
     * 分页查询
     * @return Result
     */
    @GetMapping("/page")
    public Result<IPage<FunctionSqlVO>> page(FunctionSqlDTO functionSqlDTO){
        return Result.ok(functionSqlService.page(functionSqlDTO));
    }

    /**
     * sql测试
     * @return Result
     */
    @PostMapping("/testSql")
    public Result<?> testSql(@RequestBody FunctionSqlDTO functionSqlDTO) {
        return Result.ok( functionSqlService.testSql(functionSqlDTO));
    }

    /**
     * 新增sql函数
     * @return Result
     */
    @PostMapping("/add")
    public Result<?> add(@RequestBody FunctionSqlDTO functionSqlDTO) {
        functionSqlService.add(functionSqlDTO);
        return Result.ok();
    }

    /**
     * 新增sql函数
     * @return Result
     */
    @PostMapping("/update")
    public Result<?> update(@RequestBody FunctionSqlDTO functionSqlDTO) {
        functionSqlService.update(functionSqlDTO);
        return Result.ok();
    }
}
