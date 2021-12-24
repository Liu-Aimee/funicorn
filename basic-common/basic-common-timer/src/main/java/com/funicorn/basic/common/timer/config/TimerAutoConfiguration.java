package com.funicorn.basic.common.timer.config;

import com.funicorn.basic.common.timer.core.XxlJobBeanHandlerRegister;
import com.funicorn.basic.common.timer.property.XxlJobProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Aimee
 * @since 2021/10/27 14:49
 */
@Configuration
public class TimerAutoConfiguration {

    /**
     * XxlJob 基础配置
     * @return XxlJobConfig
     * */
    @Bean
    @ConditionalOnBean(XxlJobProperties.class)
    public XxlJobConfig xxlJobConfig(){
        return new XxlJobConfig();
    }

    /**
     * 定时任务处理器自动注册器
     * @return XxlJobBeanHandlerRegister
     * */
    @Bean
    @ConditionalOnBean(XxlJobConfig.class)
    public XxlJobBeanHandlerRegister xxlJobBeanHandlerRegister(){
        return new XxlJobBeanHandlerRegister();
    }
}
