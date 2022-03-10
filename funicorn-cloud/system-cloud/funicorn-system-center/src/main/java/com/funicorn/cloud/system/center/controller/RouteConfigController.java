package com.funicorn.cloud.system.center.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.funicorn.basic.common.base.model.Result;
import com.funicorn.basic.common.security.util.SecurityUtil;
import com.funicorn.cloud.system.center.dto.RouteDTO;
import com.funicorn.cloud.system.center.dto.RouteQueryPageDTO;
import com.funicorn.cloud.system.center.entity.RouteConfig;
import com.funicorn.cloud.system.center.service.RouteConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
        return Result.ok(routeConfigService.page(new Page<>(routeQueryPageDTO.getCurrent(),routeQueryPageDTO.getSize()),queryWrapper));
    }

    @PostMapping("/checkRoute")
    public Result<?> checkRoute(@RequestBody RouteDTO routeDTO){
        List<String> routes = new ArrayList<>();
        if (routeDTO.getPredicate().endsWith("/**")) {
            routes.add(routeDTO.getPredicate());
        } else {
            routes.add(routeDTO.getPredicate());
            routes.add(routeDTO.getPredicate() + "/**");
        }
        return Result.ok("新增成功");
    }

    /**
     * 新增网关转发路由配置
     * @param routeConfig 入参
     * @return Result
     * */
    @PostMapping("/add")
    public Result<?> addRoute(@RequestBody RouteConfig routeConfig){
        return Result.ok("新增成功");
    }

    /**
     * 修改网关转发路由配置
     * @param routeConfig 入参
     * @return Result
     * */
    @PostMapping("/update")
    public Result<?> updateRoute(@RequestBody RouteConfig routeConfig){
        return Result.ok("修改成功");
    }
}

