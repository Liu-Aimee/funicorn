package com.funicorn.basic.cloud.gateway.config;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.config.GlobalCorsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * 跨域处理(采用注解的方式，此类不再使用，暂保留代码)
 * @author Aimee
 * @since 2021/10/21 14:42
 * @deprecated
 */
public class CorsAutoConfiguration {

    /**
     * 自定义跨域处理器
     * @return CorsWebFilter
     * */
    @Order(0)
    @RefreshScope
    @Bean
    public CorsWebFilter corsFilter(GlobalCorsProperties globalCorsProperties) {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        globalCorsProperties.getCorsConfigurations().forEach(source::registerCorsConfiguration);
        return new CorsWebFilter(source);
    }
}
