package com.funicorn.basic.cloud.gateway.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.funicorn.basic.cloud.gateway.entity.RouteConfig;

/**
 * 路由配置信息表业务类
 * @author Aimee
 */
public interface RouteConfigService extends IService<RouteConfig> {

    /**
     * 新增路由
     * @param routeConfig 路由信息
     * */
    void addRoute(RouteConfig routeConfig);

    /**
     * 修改路由
     * @param routeConfig 路由信息
     * */
    void updateRoute(RouteConfig routeConfig);
}
