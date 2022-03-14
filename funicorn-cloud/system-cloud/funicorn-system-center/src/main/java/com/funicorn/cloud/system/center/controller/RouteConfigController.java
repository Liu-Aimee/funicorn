package com.funicorn.cloud.system.center.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.funicorn.basic.common.base.model.Result;
import com.funicorn.basic.common.security.util.SecurityUtil;
import com.funicorn.cloud.system.center.constant.SystemConstant;
import com.funicorn.cloud.system.center.dto.RouteQueryPageDTO;
import com.funicorn.cloud.system.center.entity.RouteConfig;
import com.funicorn.cloud.system.center.exception.SystemErrorCode;
import com.funicorn.cloud.system.center.exception.SystemException;
import com.funicorn.cloud.system.center.service.RouteConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 路由配置接口
 *
 * @author Aimee
 * @since 2021-10-28
 */
@RestController
@RequestMapping("/RouteConfig")
public class RouteConfigController {

    @Resource
    private RouteConfigService routeConfigService;

    /**
     * 分页查询
     * @param routeQueryPageDTO 分页参数条件
     * @return Result
     * */
    @GetMapping("/page")
    public Result<IPage<RouteConfig>> page(RouteQueryPageDTO routeQueryPageDTO){
        LambdaQueryWrapper<RouteConfig> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(routeQueryPageDTO.getName())){
            queryWrapper.like(RouteConfig::getName,routeQueryPageDTO.getName());
        }
        if (StringUtils.isNotBlank(routeQueryPageDTO.getUri())){
            queryWrapper.like(RouteConfig::getUri,routeQueryPageDTO.getUri());
        }
        if (StringUtils.isBlank(routeQueryPageDTO.getTenantId())){
            queryWrapper.eq(RouteConfig::getTenantId,SecurityUtil.getCurrentUser().getTenantId());
        } else {
            queryWrapper.eq(RouteConfig::getTenantId,routeQueryPageDTO.getTenantId());
        }
        queryWrapper.eq(RouteConfig::getIsDelete, SystemConstant.NOT_DELETED);
        return Result.ok(routeConfigService.page(new Page<>(routeQueryPageDTO.getCurrent(),routeQueryPageDTO.getSize()),queryWrapper));
    }

    /**
     * 新增网关转发路由配置
     * @param routeConfig 入参
     * @return Result
     * */
    @PostMapping("/add")
    public Result<?> addRoute(@RequestBody RouteConfig routeConfig){
        int count = routeConfigService.count(Wrappers.<RouteConfig>lambdaQuery().eq(RouteConfig::getAppId,routeConfig.getAppId()));
        if (count>0) {
            throw new SystemException(SystemErrorCode.ROUTE_APP_IS_EXISTS);
        }

        count = routeConfigService.count(Wrappers.<RouteConfig>lambdaQuery().eq(RouteConfig::getUri,routeConfig.getUri()));
        if (count>0) {
            throw new SystemException(SystemErrorCode.ROUTE_URI_IS_EXISTS);
        }

        routeConfigService.save(routeConfig);
        return Result.ok("新增成功");
    }

    /**
     * 修改网关转发路由配置
     * @param routeConfig 入参
     * @return Result
     * */
    @PostMapping("/update")
    public Result<?> updateRoute(@RequestBody RouteConfig routeConfig){
        int count = routeConfigService.count(Wrappers.<RouteConfig>lambdaQuery().eq(RouteConfig::getAppId,routeConfig.getAppId()).ne(RouteConfig::getId,routeConfig.getId()));
        if (count>0) {
            throw new SystemException(SystemErrorCode.ROUTE_APP_IS_EXISTS);
        }

        count = routeConfigService.count(Wrappers.<RouteConfig>lambdaQuery().eq(RouteConfig::getUri,routeConfig.getUri()).ne(RouteConfig::getId,routeConfig.getId()));
        if (count>0) {
            throw new SystemException(SystemErrorCode.ROUTE_URI_IS_EXISTS);
        }
        routeConfigService.updateById(routeConfig);
        return Result.ok("修改成功");
    }

    /**
     * 装载网关转发路由配置
     * @param routeId 路由id
     * @return Result
     * */
    @PutMapping("/reload/{routeId}")
    public Result<?> reloadRoute(@PathVariable("routeId") String routeId){
        routeConfigService.reloadRoute(routeId);
        return Result.ok("重载成功");
    }

    /**
     * 卸载网关转发路由配置
     * @param routeId 路由id
     * @return Result
     * */
    @DeleteMapping("/uninstall/{routeId}")
    public Result<?> uninstallRoute(@PathVariable("routeId") String routeId){
        routeConfigService.uninstallRoute(routeId);
        return Result.ok("卸载成功");
    }

    /**
     * 删除网关转发路由配置
     * @param routeId 路由id
     * @return Result
     * */
    @DeleteMapping("/remove/{routeId}")
    public Result<?> removeRoute(@PathVariable("routeId") String routeId){
        routeConfigService.removeRoute(routeId);
        return Result.ok("已删除");
    }
}

