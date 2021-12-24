package com.funicorn.basic.common.rocketmq.annotation;

import com.funicorn.basic.common.rocketmq.config.ProducerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Aimee
 * @since 2021/12/22 18:25
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import(ProducerConfiguration.class)
public @interface EnableRocketMQProducer {
}
