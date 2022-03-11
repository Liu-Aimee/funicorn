package com.funicorn.basic.gateway.api.service;

import com.funicorn.basic.common.base.model.Result;
import com.funicorn.basic.gateway.api.fallback.GatewayServiceFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @author Aimee
 * @since 2022/3/10 13:41
 */
@Import(GatewayServiceFallBackFactory.class)
@FeignClient(name = "basic-cloud-gateway", fallbackFactory = GatewayServiceFallBackFactory.class)
public interface GatewayService {

    /**
     * 新增网关转发路由配置
     * @param routeId 路由id
     * @return Result
     * */
    @PutMapping("/Route/reload/{routeId}")
    Result<?> reloadRoute(@PathVariable("routeId") String routeId);

    /**
     * 卸载网关转发路由配置
     * @param routeId 路由id
     * @return Result
     * */
    @DeleteMapping("/Route/uninstall/{routeId}")
    Result<?> uninstallRoute(@PathVariable("routeId") String routeId);
}
