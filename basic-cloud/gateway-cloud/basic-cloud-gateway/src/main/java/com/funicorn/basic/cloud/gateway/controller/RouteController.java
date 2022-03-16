package com.funicorn.basic.cloud.gateway.controller;

import com.funicorn.basic.cloud.gateway.service.RouteConfigService;
import com.funicorn.basic.common.base.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 路由配置接口
 * @author Aimee
 * @since 2021/10/27 13:12
 */
@Slf4j
@RestController
@RequestMapping("/Route")
public class RouteController {

    @Resource
    private RouteConfigService routeConfigService;

    /**
     * 修改网关转发路由配置
     * @param routeId 路由id
     * @return Result
     * */
    @PutMapping("/reload/{routeId}")
    public Result<?> reloadRoute(@PathVariable("routeId") String routeId){
        try {
            routeConfigService.reloadRoute(routeId);
        } catch (Exception e) {
            log.error("路由加载失败",e);
            return Result.error(e.getMessage());
        }
        return Result.ok("重载成功");
    }

    /**
     * 修改网关转发路由配置
     * @param routeId 路由id
     * @return Result
     * */
    @DeleteMapping("/uninstall/{routeId}")
    public Result<?> uninstallRoute(@PathVariable("routeId") String routeId){
        routeConfigService.uninstallRoute(routeId);
        return Result.ok("卸载成功");
    }
}
