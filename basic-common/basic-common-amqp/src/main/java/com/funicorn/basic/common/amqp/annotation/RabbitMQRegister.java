package com.funicorn.basic.common.amqp.annotation;

import java.lang.annotation.*;

/**
 * @author Aimee
 * @since 2021/10/08
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface RabbitMQRegister {

    /**
     * 交换机
     * @return String
     * */
    String exchange();

    /**
     * 队列
     * @return String
     * */
    String queue();

    /**
     * 路由
     * @return String
     * */
    String routing();
}
