package com.funicorn.cloud.system.center.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.funicorn.basic.common.base.model.Result;
import com.funicorn.cloud.system.center.dto.FilterQueryPageDTO;
import com.funicorn.cloud.system.center.dto.PredicateDTO;
import com.funicorn.cloud.system.center.entity.RouteFilter;
import com.funicorn.cloud.system.center.exception.SystemErrorCode;
import com.funicorn.cloud.system.center.exception.SystemException;
import com.funicorn.cloud.system.center.service.RouteFilterService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 *  网关路由过滤器管理
 *
 * @author Aimee
 * @since 2022-03-09
 */
@RestController
@RequestMapping("/Filter")
public class RouteFilterController {

    @Resource
    private RouteFilterService routeFilterService;

    /**
     * 分页查询过滤器
     * @param filterQueryPageDTO 入参
     * @return Result
     * */
    @GetMapping("/page")
    private Result<IPage<RouteFilter>> page(FilterQueryPageDTO filterQueryPageDTO){
        IPage<RouteFilter> iPage = routeFilterService.page(new Page<>(filterQueryPageDTO.getCurrent(),filterQueryPageDTO.getSize()),
                Wrappers.<RouteFilter>lambdaQuery().eq(RouteFilter::getRouteId,filterQueryPageDTO.getRouteId()));
        return Result.ok(iPage);
    }

    /**
     * 新增过滤器
     * @param routeFilter 入参
     * @return Result
     * */
    @PostMapping("/add")
    public Result<?> add(@RequestBody RouteFilter routeFilter){
        int count = routeFilterService.count(Wrappers.<RouteFilter>lambdaQuery()
                .eq(RouteFilter::getRouteId,routeFilter.getRouteId()).eq(RouteFilter::getType,routeFilter.getType()));
        if (count>0) {
            throw new SystemException(SystemErrorCode.ROUTE_FILTER_TYPE_IS_EXISTS);
        }
        routeFilterService.save(routeFilter);
        return Result.ok();
    }

    /**
     * 修改过滤器
     * @param predicateDTO 入参
     * @return Result
     * */
    @PostMapping("/update")
    public Result<?> update(@RequestBody PredicateDTO predicateDTO){
        RouteFilter routeFilter = new RouteFilter();
        routeFilter.setId(predicateDTO.getId());
        routeFilter.setValue(predicateDTO.getValue());
        routeFilterService.updateById(routeFilter);
        return Result.ok();
    }

    /**
     * 修改断言启用状态
     * @param filterId 入参
     * @return Result
     * */
    @PutMapping("/changeStatus/{filterId}")
    public Result<?> changeStatus(@PathVariable String filterId){
        routeFilterService.changeStatus(filterId);
        return Result.ok();
    }

    /**
     * 删除过滤器
     * @param id 断言id
     * @return Result
     * */
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable("id") String id){
        routeFilterService.removeById(id);
        return Result.ok();
    }
}

