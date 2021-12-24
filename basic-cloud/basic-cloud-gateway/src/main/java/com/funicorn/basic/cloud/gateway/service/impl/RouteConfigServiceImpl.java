package com.funicorn.basic.cloud.gateway.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funicorn.basic.cloud.gateway.config.GatewayRouteConfig;
import com.funicorn.basic.cloud.gateway.mapper.RouteConfigMapper;
import com.funicorn.basic.cloud.gateway.service.RouteConfigService;
import com.funicorn.basic.cloud.gateway.entity.RouteConfig;
import com.funicorn.basic.cloud.gateway.exception.GatewayErrorCode;
import com.funicorn.basic.cloud.gateway.exception.GatewayException;
import org.apache.commons.lang3.StringUtils;
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

    @Override
    public void addRoute(RouteConfig routeConfig) {

        int uriCount = baseMapper.selectCount(Wrappers.<RouteConfig>lambdaQuery().eq(RouteConfig::getUri,routeConfig.getUri()));
        if (uriCount>0){
            throw new GatewayException(GatewayErrorCode.URI_IS_EXISTS);
        }
        int predicatesCount = baseMapper.selectCount(Wrappers.<RouteConfig>lambdaQuery().eq(RouteConfig::getPredicates,routeConfig.getPredicates()));
        if (predicatesCount>0){
            throw new GatewayException(GatewayErrorCode.PREDICATES_IS_EXISTS);
        }
        if (StringUtils.isBlank(routeConfig.getFilters())){
            routeConfig.setFilters("1");
        }
        if (routeConfig.getOrder()==null){
            routeConfig.setOrder(0);
        }

        baseMapper.insert(routeConfig);
        gatewayRouteConfig.saveRoute(routeConfig);
    }

    @Override
    public void updateRoute(RouteConfig routeConfig) {
        int uriCount = baseMapper.selectCount(Wrappers.<RouteConfig>lambdaQuery().eq(RouteConfig::getUri,routeConfig.getUri()).ne(RouteConfig::getId,routeConfig.getId()));
        if (uriCount>0){
            throw new GatewayException(GatewayErrorCode.URI_IS_EXISTS);
        }
        int predicatesCount = baseMapper.selectCount(Wrappers.<RouteConfig>lambdaQuery().eq(RouteConfig::getPredicates,routeConfig.getPredicates()).ne(RouteConfig::getId,routeConfig.getId()));
        if (predicatesCount>0){
            throw new GatewayException(GatewayErrorCode.PREDICATES_IS_EXISTS);
        }
        baseMapper.updateById(routeConfig);
        gatewayRouteConfig.updateRoute(routeConfig);
    }
}