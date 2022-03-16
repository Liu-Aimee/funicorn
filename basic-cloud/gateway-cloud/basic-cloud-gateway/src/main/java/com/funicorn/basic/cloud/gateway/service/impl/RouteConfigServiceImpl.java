package com.funicorn.basic.cloud.gateway.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funicorn.basic.cloud.gateway.config.GatewayRouteConfig;
import com.funicorn.basic.cloud.gateway.entity.RouteConfig;
import com.funicorn.basic.cloud.gateway.entity.RoutePredicate;
import com.funicorn.basic.cloud.gateway.exception.GatewayErrorCode;
import com.funicorn.basic.cloud.gateway.exception.GatewayException;
import com.funicorn.basic.cloud.gateway.mapper.RouteConfigMapper;
import com.funicorn.basic.cloud.gateway.service.RouteConfigService;
import com.funicorn.basic.cloud.gateway.service.RoutePredicateService;
import com.funicorn.basic.common.base.util.JsonUtil;
import com.funicorn.basic.gateway.api.model.Predicate;
import com.funicorn.basic.gateway.api.util.RouteValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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
    @Resource
    private RoutePredicateService routePredicateService;

    @Override
    public void reloadRoute(String routeId) {
        RouteConfig routeConfig = routeConfigService.getById(routeId);
        if (routeConfig==null) {
            throw new GatewayException(GatewayErrorCode.ROUTE_IS_NOT_FOUND);
        }

        List<RoutePredicate> predicates = routePredicateService.list(Wrappers.<RoutePredicate>lambdaQuery().eq(RoutePredicate::getRouteId,routeId));
        if (predicates==null || predicates.isEmpty()) {
            throw new GatewayException(GatewayErrorCode.PREDICATES_IS_NOT_FOUND);
        }
        //断言校验
        predicates.forEach(predicate -> RouteValidator.validatePredicate(JsonUtil.object2Object(predicate, Predicate.class)));
        gatewayRouteConfig.updateRoute(routeConfig);
    }

    @Override
    public void uninstallRoute(String routeId) {
        RouteConfig routeConfig = routeConfigService.getById(routeId);
        if (routeConfig==null) {
            throw new GatewayException(GatewayErrorCode.ROUTE_IS_NOT_FOUND);
        }
        gatewayRouteConfig.removeRoute(routeId);
    }
}