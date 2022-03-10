package com.funicorn.cloud.system.center.service.impl;

import com.funicorn.basic.common.base.model.Result;
import com.funicorn.basic.gateway.api.service.GatewayService;
import com.funicorn.cloud.system.center.constant.SystemConstant;
import com.funicorn.cloud.system.center.entity.RouteConfig;
import com.funicorn.cloud.system.center.exception.SystemErrorCode;
import com.funicorn.cloud.system.center.exception.SystemException;
import com.funicorn.cloud.system.center.mapper.RouteConfigMapper;
import com.funicorn.cloud.system.center.service.RouteConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 路由配置信息表 服务实现类
 * </p>
 *
 * @author Aimee
 * @since 2021-10-28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RouteConfigServiceImpl extends ServiceImpl<RouteConfigMapper, RouteConfig> implements RouteConfigService {

    @Resource
    private GatewayService gatewayService;

    @Override
    public void reloadRoute(String routeId) {
        RouteConfig routeConfig = super.getById(routeId);
        if (routeConfig==null) {
            return;
        }
        Result<?> result = gatewayService.reloadRoute(routeId);
        if (result==null || !result.isSuccess()) {
            throw new SystemException(SystemErrorCode.ROUTE_RELOAD_FAILED);
        }

        RouteConfig updateRoute = new RouteConfig();
        updateRoute.setId(routeId);
        updateRoute.setStatus(SystemConstant.ROUTE_STATUS_ON);
        updateById(routeConfig);
    }

    @Override
    public void uninstallRoute(String routeId) {
        RouteConfig routeConfig = super.getById(routeId);
        if (routeConfig==null) {
            return;
        }
        Result<?> result = gatewayService.uninstallRoute(routeId);
        if (result==null || !result.isSuccess()) {
            throw new SystemException(SystemErrorCode.ROUTE_UNINSTALL_FAILED);
        }

        RouteConfig updateRoute = new RouteConfig();
        updateRoute.setId(routeId);
        updateRoute.setStatus(SystemConstant.ROUTE_STATUS_OFF);
        updateById(routeConfig);
    }
}
