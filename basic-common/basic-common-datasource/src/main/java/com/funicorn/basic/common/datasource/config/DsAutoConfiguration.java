package com.funicorn.basic.common.datasource.config;

import com.funicorn.basic.common.datasource.filter.QueryEscapeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Aimee
 * @since 2021/10/27 11:14
 */
@Configuration
public class DsAutoConfiguration {

    /**
     * sql查询全局拦截器
     * @return QueryEscapeInterceptor
     * */
    @Bean
    public QueryEscapeInterceptor queryEscapeInterceptor(){
        return new QueryEscapeInterceptor();
    }
}
