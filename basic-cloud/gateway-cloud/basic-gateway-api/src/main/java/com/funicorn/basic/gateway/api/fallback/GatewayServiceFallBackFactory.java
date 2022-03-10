package com.funicorn.basic.gateway.api.fallback;

import com.funicorn.basic.gateway.api.service.GatewayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author Aimee
 * @since 2022/3/10 13:42
 */
@Slf4j
@Component
public class GatewayServiceFallBackFactory implements FallbackFactory<GatewayService> {
    @Override
    public GatewayService create(Throwable cause) {
        return null;
    }
}
