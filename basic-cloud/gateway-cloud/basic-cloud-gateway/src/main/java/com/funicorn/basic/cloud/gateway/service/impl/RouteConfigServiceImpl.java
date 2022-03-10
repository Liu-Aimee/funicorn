package com.funicorn.basic.cloud.gateway.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funicorn.basic.cloud.gateway.config.GatewayRouteConfig;
import com.funicorn.basic.cloud.gateway.entity.RouteConfig;
import com.funicorn.basic.cloud.gateway.mapper.RouteConfigMapper;
import com.funicorn.basic.cloud.gateway.service.RouteConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 路由配置信息表业务实现类
 * @author Aimee
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RouteConfigServiceImpl extends ServiceImpl<RouteConfigMapper, RouteConfig> implements RouteConfigService {

    @Resource
    private GatewayRouteConfig gatewayRouteConfig;
    @Resource
    private RouteConfigService routeConfigService;

    @Override
    public void reloadRoute(String routeId) {
        RouteConfig routeConfig = routeConfigService.getById(routeId);
        if (routeConfig==null) {
            return;
        }
        gatewayRouteConfig.updateRoute(routeConfig);
    }
}