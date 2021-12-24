package com.funicorn.basic.cloud.gateway.controller;

import com.funicorn.basic.cloud.gateway.entity.RouteConfig;
import com.funicorn.basic.cloud.gateway.service.RouteConfigService;
import com.funicorn.basic.common.base.model.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 路由配置接口
 * @author Aimee
 * @since 2021/10/27 13:12
 */
@RestController
@RequestMapping("/Route")
public class RouteController {

    @Resource
    private RouteConfigService routeConfigService;

    /**
     * 新增网关转发路由配置
     * @param routeConfig 入参
     * @return Result
     * */
    @PostMapping("/add")
    public Result<?> addRoute(@RequestBody RouteConfig routeConfig){
        routeConfigService.addRoute(routeConfig);
        return Result.ok("新增成功");
    }

    /**
     * 修改网关转发路由配置
     * @param routeConfig 入参
     * @return Result
     * */
    @PostMapping("/update")
    public Result<?> updateRoute(@RequestBody RouteConfig routeConfig){
        routeConfigService.updateRoute(routeConfig);
        return Result.ok("修改成功");
    }
}
