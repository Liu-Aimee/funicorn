package com.funicorn.basic.common.redis.config;

import com.funicorn.basic.common.redis.property.RedisConfigProperties;
import com.funicorn.basic.common.redis.util.RedisUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Aimee
 * @since 2021/12/21 16:31
 */
@Configuration
@EnableConfigurationProperties({RedisConfigProperties.class})
public class RedisAutoConfiguration {

    @ConditionalOnBean(RedisConfig.class)
    @Bean
    public RedisUtil redisUtil(){
        return new RedisUtil();
    }
}
