package com.funicorn.basic.common.security.config;

import com.funicorn.basic.common.security.filter.ContextHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * 拦截器管理
 * @author Aimee
 * @since 2021/9/29 8:35
 */
@Configuration
@Import(ContextHandlerInterceptor.class)
public class SecurityWebMvcConfig implements WebMvcConfigurer {

    @Resource
    private ContextHandlerInterceptor contextHandlerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加拦截器，配置拦截地址
        registry.addInterceptor(contextHandlerInterceptor).addPathPatterns("/**");
    }
}
