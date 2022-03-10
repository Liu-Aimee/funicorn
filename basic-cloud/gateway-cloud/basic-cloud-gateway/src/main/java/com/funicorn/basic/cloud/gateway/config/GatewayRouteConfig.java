package com.funicorn.basic.cloud.gateway.config;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.funicorn.basic.cloud.gateway.constant.GatewayConstant;
import com.funicorn.basic.cloud.gateway.entity.RouteConfig;
import com.funicorn.basic.cloud.gateway.entity.RouteFilter;
import com.funicorn.basic.cloud.gateway.entity.RoutePredicate;
import com.funicorn.basic.cloud.gateway.service.RouteConfigService;
import com.funicorn.basic.cloud.gateway.service.RouteFilterService;
import com.funicorn.basic.cloud.gateway.service.RoutePredicateService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Aimee
 * @since 2020/8/11 17:16
 */
@Component
@Slf4j
public class GatewayRouteConfig implements ApplicationEventPublisherAware, CommandLineRunner {

    private static final String HTTP_STR = "http";

    private static final String LB_STR = "lb";

    private ApplicationEventPublisher publisher;

    @Resource
    private RedisRouteDefinitionRepository routeDefinitionWriter;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    @Resource
    private RouteConfigService routeConfigService;
    @Resource
    private RoutePredicateService routePredicateService;
    @Resource
    private RouteFilterService routeFilterService;

    @SneakyThrows
    @Override
    public void run(String... args) {
        loadRoute();
    }

    @Override
    public void setApplicationEventPublisher(@NonNull ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    /**
     * redis加载的路由配置信息
     * */
    public void loadRoute(){
        redisTemplate.delete(GatewayConstant.GATEWAY_ROUTES);

        List<RouteConfig> routeConfigs = routeConfigService.list(Wrappers.<RouteConfig>query().lambda().eq(RouteConfig::getIsDelete,"0"));
        routeConfigs.forEach(routeConfig -> {
            RouteDefinition definition = initDefinition(routeConfig);
            if (definition==null){
                return;
            }
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            log.info("[" + definition.getPredicates() + "]---->" + "[" + definition.getUri() + "]");
        });

        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    public void saveRoute(RouteConfig routeConfig){
        RouteDefinition definition=initDefinition(routeConfig);
        if (definition==null){
            return;
        }
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    public void updateRoute(RouteConfig routeConfig) {
        RouteDefinition definition=initDefinition(routeConfig);
        if (definition==null){
            return;
        }
        try {
            this.routeDefinitionWriter.delete(Mono.just(definition.getId()));
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void removeRoute(String routeId){
        routeDefinitionWriter.delete(Mono.just(routeId)).subscribe();
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    /**
    * 数据转换对象 RouteDefinition
    * */
    private RouteDefinition initDefinition(RouteConfig routeConfig){
        RouteDefinition definition = new RouteDefinition();
        URI uri;
        if (StringUtils.isBlank(routeConfig.getUri())){
            return null;
        }
        if(routeConfig.getUri().startsWith(HTTP_STR)){
            //http地址 或者 lb://
            uri = UriComponentsBuilder.fromHttpUrl(routeConfig.getUri()).build().toUri();
        } else if (routeConfig.getUri().startsWith(LB_STR)){
            //注册中心模式
            uri = UriComponentsBuilder.fromUriString(routeConfig.getUri()).build().toUri();
        } else {
            //默认注册中心模式
            uri = UriComponentsBuilder.fromUriString("lb://"+routeConfig.getUri()).build().toUri();
        }

        List<RoutePredicate> routePredicates = routePredicateService.list(Wrappers.<RoutePredicate>lambdaQuery().eq(RoutePredicate::getRouteId,routeConfig.getId()));
        if (routePredicates==null || routePredicates.isEmpty()) {
            return null;
        }

        //spring gateway会根据名称找对应的PredicateFactory
        List<PredicateDefinition> predicateDefinitions = new ArrayList<>();
        for (RoutePredicate routePredicate : routePredicates) {
            PredicateDefinition predicateDefinition = new PredicateDefinition();
            predicateDefinition.setName(routePredicate.getType());
            Map<String, String> pattern = new HashMap<>(16);
            pattern.put("pattern", routePredicate.getValue());
            predicateDefinition.setArgs(pattern);
            predicateDefinitions.add(predicateDefinition);
        }

        List<RouteFilter> routeFilters = routeFilterService.list(Wrappers.<RouteFilter>lambdaQuery().eq(RouteFilter::getRouteId,routeConfig.getId()));
        if (routeFilters==null || routeFilters.isEmpty()) {
            return null;
        }

        //spring gateway会根据名称找对应的FilterFactory
        List<FilterDefinition> filterDefinitions = new ArrayList<>();
        int i=0;
        for (RouteFilter routeFilter : routeFilters) {
            FilterDefinition filterDefinition = new FilterDefinition();
            filterDefinition.setName(routeFilter.getType());
            Map<String, String> filterParams = new HashMap<>(8);
            filterParams.put("_genkey_" + i, routeFilter.getValue());
            filterDefinition.setArgs(filterParams);
            filterDefinitions.add(filterDefinition);
            i++;
        }

        definition.setId(routeConfig.getAppId());
        definition.setUri(uri);
        definition.setPredicates(predicateDefinitions);
        definition.setFilters(filterDefinitions);
        definition.setOrder(routeConfig.getOrder()==null ? 0 : routeConfig.getOrder());
        return definition;
    }
}
