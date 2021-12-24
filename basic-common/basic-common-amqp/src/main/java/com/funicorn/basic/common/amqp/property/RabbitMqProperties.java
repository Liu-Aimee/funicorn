package com.funicorn.basic.common.amqp.property;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Aimee
 * @since 2021/12/24 16:09
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitMqProperties {

    /**
     * 生产者队列
     * */
    private List<Topic> producers;

    /**
     * 消费者队列
     * */
    private List<Topic> consumers;

    @Data
    @ToString
    public static class Topic{

        /**
         * 队列名
         * */
        private String queue;

        /**
         * 交换机
         * */
        private String exchange;

        /**
         * 路由
         * */
        private String routing;
    }
}
