package com.funicorn.basic.common.rocketmq.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Aimee
 * @since 2021/12/22 14:14
 */
@Configuration
@ConfigurationProperties(prefix = "rocketmq")
@Data
public class RocketMqProperties {

    /**
     * 服务器地址
     * */
    private String nameServer;

    /**
     * 生产者配置
     * */
    private Producer producer;

    /**
     * 消费者配置
     * */
    private Consumer consumer;

    @Data
    public static class Producer{

        /**
         * 生产者分组名称
         * */
        private String groupName;

        /**
         * 订阅主题
         * */
        private String topic;

        /**
         * 同步消息tag
         * */
        private String syncTag;

        /**
         * 异步消息tag
         * */
        private String asyncTag;

        /**
         * 单向消息tag
         * */
        private String onewayTag;

        /**
         * 消息最大值
         */
        private Integer maxMessageSize = 4096;

        /**
         * 消息发送超时时间
         */
        private Integer sendMsgTimeOut = 3000;

        /**
         * 失败重试次数
         */
        private Integer retryTimesWhenSendFailed = 3;
    }

    /**
     * 消费者配置
     * */
    @Data
    public static class Consumer{
        /**
         * 生产者分组名称
         * */
        private String groupName;

        /**
         * 订阅主题和tag topic:sync_tag 订阅所有用* topic:*
         * */
        private List<String> topicTags;

        /**
         * 监听多个tag时使用 || 进行分割，如果监听所有使用*或者不填
         * */
        private String tags;

        /**
         * 消费者最小线程数据量
         * */
        private Integer consumeThreadMin = 20;

        /**
         * 消费者最大线程数据量
         * */
        private Integer consumeThreadMax = 100;

        /**
         * 消费重试次数
         * */
        private Integer consumeMessageBatchMaxSize = 3;
    }
}
