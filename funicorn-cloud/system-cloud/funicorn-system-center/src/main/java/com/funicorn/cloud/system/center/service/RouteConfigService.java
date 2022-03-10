package com.funicorn.cloud.system.center.service;

import com.funicorn.cloud.system.center.entity.RouteConfig;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 路由配置信息表 服务类
 * </p>
 *
 * @author Aimee
 * @since 2021-10-28
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
