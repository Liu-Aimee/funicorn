package com.funicorn.basic.common.base.config;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.funicorn.basic.common.base.exception.BaseExceptionHandler;
import com.funicorn.basic.common.base.property.FunicornConfigProperties;
import com.funicorn.basic.common.base.property.ThreadPoolProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Aimee
 * @since 2021/10/26 17:03
 */
@Configuration
@EnableAsync
@EnableConfigurationProperties({FunicornConfigProperties.class, ThreadPoolProperties.class})
public class BaseAutoConfiguration {

    @Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
    private String pattern;

    @Resource
    private FunicornConfigProperties funicornConfigProperties;
    @Resource
    private ThreadPoolProperties threadPoolProperties;

    /**
     * 白名单
     * @return WhiteUrlConfig
     * */
    @Bean
    public WhiteUrlConfig whiteUrlConfig() {
        return new WhiteUrlConfig(funicornConfigProperties);
    }

    /**
     * LocalDateTime与json互转解析器
     * @return Jackson2ObjectMapperBuilderCustomizer
     * */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizeLocalDateTimeFormat() {
        return jacksonObjectMapperBuilder -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDateTimeDeserializer deserializer = new LocalDateTimeDeserializer(formatter);
            LocalDateTimeSerializer serializer = new LocalDateTimeSerializer(formatter);
            jacksonObjectMapperBuilder.serializerByType(LocalDateTime.class, serializer);
            jacksonObjectMapperBuilder.deserializerByType(LocalDateTime.class, deserializer);
        };
    }

    /**
     * CommonBaseException异常拦截器
     * @return SecurityExceptionHandler
     * */
    @Bean
    public BaseExceptionHandler baseExceptionHandler(){
        return new BaseExceptionHandler();
    }

    /**
     * 初始化线程池
     * @return ThreadPoolTaskExecutor
     * */
    @Primary
    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(threadPoolProperties.getCorePoolSize()!=null ? threadPoolProperties.getCorePoolSize() : 10);
        executor.setMaxPoolSize(threadPoolProperties.getMaxPoolSize()!=null ? threadPoolProperties.getMaxPoolSize() : 20);
        executor.setQueueCapacity(threadPoolProperties.getQueueCapacity()!=null ? threadPoolProperties.getQueueCapacity() : 200);
        executor.setKeepAliveSeconds(threadPoolProperties.getKeepAliveSeconds()!=null ? threadPoolProperties.getKeepAliveSeconds() :60);
        executor.setThreadNamePrefix(StringUtils.isNotBlank(threadPoolProperties.getPrefix()) ? threadPoolProperties.getPrefix() : "taskExecutor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        return executor;
    }
}
