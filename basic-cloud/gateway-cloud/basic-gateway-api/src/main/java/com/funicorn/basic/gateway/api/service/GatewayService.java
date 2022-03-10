package com.funicorn.basic.gateway.api.service;

import com.funicorn.basic.common.base.model.Result;
import com.funicorn.basic.gateway.api.fallback.GatewayServiceFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Aimee
 * @since 2022/3/10 13:41
 */
@Import(GatewayServiceFallBackFactory.class)
@FeignClient(name = "basic-gateway-api", fallbackFactory = GatewayServiceFallBackFactory.class)
public interface GatewayService {

    /**
     * 新增网关转发路由配置
     * @param routeId 路由id
     * @return Result
     * */
    @PatchMapping("/Route/reload/{routeId}")
    Result<?> reloadRoute(@PathVariable("routeId") String routeId);
}
