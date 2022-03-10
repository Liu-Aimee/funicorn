package com.funicorn.cloud.system.center.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.funicorn.basic.common.base.model.Result;
import com.funicorn.cloud.system.center.dto.PredicateDTO;
import com.funicorn.cloud.system.center.dto.PredicateQueryPageDTO;
import com.funicorn.cloud.system.center.entity.RoutePredicate;
import com.funicorn.cloud.system.center.exception.SystemErrorCode;
import com.funicorn.cloud.system.center.exception.SystemException;
import com.funicorn.cloud.system.center.service.RoutePredicateService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 *  网关路由断言管理
 *
 * @author Aimee
 * @since 2022-03-09
 */
@RestController
@RequestMapping("/Predicate")
public class RoutePredicateController {

    @Resource
    private RoutePredicateService routePredicateService;

    /**
     * 分页查询断言
     * @param predicateQueryPageDTO 入参
     * @return Result
     * */
    @GetMapping("/page")
    private Result<IPage<RoutePredicate>> page(PredicateQueryPageDTO predicateQueryPageDTO){
        IPage<RoutePredicate> iPage = routePredicateService.page(new Page<>(predicateQueryPageDTO.getCurrent(),predicateQueryPageDTO.getSize()),
                Wrappers.<RoutePredicate>lambdaQuery().eq(RoutePredicate::getRouteId,predicateQueryPageDTO.getRouteId()));
        return Result.ok(iPage);
    }

    /**
     * 新增断言
     * @param routePredicate 入参
     * @return Result
     * */
    @PostMapping("/add")
    public Result<?> add(@RequestBody RoutePredicate routePredicate){
        int count = routePredicateService.count(Wrappers.<RoutePredicate>lambdaQuery()
                .eq(RoutePredicate::getRouteId,routePredicate.getRouteId()).eq(RoutePredicate::getType,routePredicate.getType()));
        if (count>0) {
            throw new SystemException(SystemErrorCode.ROUTE_PREDICATE_TYPE_IS_EXISTS);
        }
        routePredicateService.save(routePredicate);
        return Result.ok();
    }

    /**
     * 修改断言
     * @param predicateDTO 入参
     * @return Result
     * */
    @PostMapping("/update")
    public Result<?> update(@RequestBody PredicateDTO predicateDTO){
        RoutePredicate routePredicate = new RoutePredicate();
        routePredicate.setId(predicateDTO.getId());
        routePredicate.setValue(predicateDTO.getValue());
        routePredicateService.updateById(routePredicate);
        return Result.ok();
    }

    /**
     * 删除断言
     * @param id 断言id
     * @return Result
     * */
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable("id") String id){
        routePredicateService.removeById(id);
        return Result.ok();
    }
}

