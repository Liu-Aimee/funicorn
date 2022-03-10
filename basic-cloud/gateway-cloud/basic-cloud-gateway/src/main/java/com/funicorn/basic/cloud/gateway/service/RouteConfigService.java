package com.funicorn.basic.cloud.gateway.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.funicorn.basic.cloud.gateway.entity.RouteConfig;

/**
 * 路由配置信息表业务类
 * @author Aimee
 */
public interface RouteConfigService extends IService<RouteConfig> {

    /**
     * 重载路由
     * @param routeId 路由id
     * */
    void reloadRoute(String routeId);

    /**
     * 卸载路由
     * @param routeId 路由id
     * */
    void uninstallRoute(String routeId);
}
