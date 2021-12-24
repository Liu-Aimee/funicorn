package com.funicorn.basic.common.base.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Aimee
 * @since 2021/11/9 15:00
 */
@Data
@Configuration
@ConfigurationProperties("thread")
public class ThreadPoolProperties {

    /**
     * 线程前缀
     * */
    private String prefix;

    /**
     * 核心线程数
     * */
    private Integer corePoolSize;

    /**
     * 最大线程数
     * */
    private Integer maxPoolSize;

    /**
     * 缓冲队列大小
     * */
    private Integer queueCapacity;

    /**
     * 线程空闲时间
     * */
    private Integer keepAliveSeconds;
}
