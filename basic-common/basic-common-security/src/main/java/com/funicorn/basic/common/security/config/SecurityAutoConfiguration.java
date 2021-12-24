package com.funicorn.basic.common.security.config;

import com.funicorn.basic.common.base.property.FunicornConfigProperties;
import com.funicorn.basic.common.security.endpoint.CustomizeTokenEndpoint;
import com.funicorn.basic.common.security.exception.SecurityExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Aimee
 * @since 2021/10/28 14:36
 */
@Configuration
public class SecurityAutoConfiguration {

    /**
     * SecurityException异常拦截器
     * @return SecurityExceptionHandler
     * */
    @Bean
    public SecurityExceptionHandler securityExceptionHandler(){
        return new SecurityExceptionHandler();
    }

    /**
     * 自定义code换取令牌端点
     * @return CustomizeTokenEndpoint
     * */
    @ConditionalOnBean(FunicornConfigProperties.class)
    @Bean
    public CustomizeTokenEndpoint customizeTokenEndpoint() {
        return new CustomizeTokenEndpoint();
    }
}
