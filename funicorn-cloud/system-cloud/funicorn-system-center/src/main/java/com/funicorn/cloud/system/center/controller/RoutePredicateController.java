package com.funicorn.cloud.system.center.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.funicorn.basic.common.base.model.Result;
import com.funicorn.basic.common.base.util.JsonUtil;
import com.funicorn.basic.gateway.api.model.Predicate;
import com.funicorn.basic.gateway.api.util.RouteValidator;
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
                .eq(RoutePredicate::getRouteId,routePredicate.getRouteId())
                .eq(RoutePredicate::getType,routePredicate.getType()));
        if (count>0) {
            throw new SystemException(SystemErrorCode.ROUTE_PREDICATE_TYPE_IS_EXISTS);
        }

        try {
            RouteValidator.validatePredicate(JsonUtil.object2Object(routePredicate, Predicate.class));
        } catch (Exception e) {
            return Result.error(e.getMessage());
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
        int count = routePredicateService.count(Wrappers.<RoutePredicate>lambdaQuery()
                .eq(RoutePredicate::getRouteId,predicateDTO.getRouteId())
                .eq(RoutePredicate::getType,predicateDTO.getType())
                .ne(RoutePredicate::getId,predicateDTO.getId()));
        if (count>0) {
            throw new SystemException(SystemErrorCode.ROUTE_PREDICATE_TYPE_IS_EXISTS);
        }

        RoutePredicate updatePredicate = new RoutePredicate();
        updatePredicate.setId(predicateDTO.getId());
        updatePredicate.setType(predicateDTO.getType());
        updatePredicate.setValue(predicateDTO.getValue());
        try {
            RouteValidator.validatePredicate(JsonUtil.object2Object(updatePredicate, Predicate.class));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
        routePredicateService.updateById(updatePredicate);
        return Result.ok();
    }

    /**
     * 修改断言启用状态
     * @param predicateId 入参
     * @return Result
     * */
    @PutMapping("/changeStatus/{predicateId}")
    public Result<?> changeStatus(@PathVariable String predicateId){
        routePredicateService.changeStatus(predicateId);
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

