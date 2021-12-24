package com.funicorn.cloud.chart.center.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.funicorn.basic.common.base.model.Result;
import com.funicorn.cloud.chart.center.constant.ChartConstant;
import com.funicorn.cloud.chart.center.entity.AnalysisProject;
import com.funicorn.cloud.chart.center.service.AnalysisProjectService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 项目管理接口
 * @author Aimee
 * @since 2021/12/03
 */
@RestController
@RequestMapping("/AnalysisProject")
public class AnalysisProjectController {

    @Resource
    private AnalysisProjectService analysisProjectService;

    /**
     * 查询所有项目
     * @return Result
     */
    @GetMapping("/list")
    public Result<List<AnalysisProject>> list() {
        List<AnalysisProject> list = analysisProjectService.list(Wrappers.<AnalysisProject>lambdaQuery()
                .eq(AnalysisProject::getIsDelete, ChartConstant.NOT_DELETED));
        return Result.ok(list);
    }

    /**
     * 新增项目
     * @param analysisProject 入参
     * @return Result
     */
    @PostMapping("/add")
    public Result<AnalysisProject> save(@RequestBody AnalysisProject analysisProject) {
        analysisProjectService.save(analysisProject);
        return Result.ok(analysisProject);
    }

}
