package com.funicorn.basic.common.mqtt.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Aimee
 * @since 2021/12/23 9:27
 */
@Configuration
@ConfigurationProperties(prefix = "mqtt")
@Data
public class MqttProperties {

    /**
     * 驱动数组
     * */
    private List<Driver> drivers;

    @Data
    public static class Driver{

        /**
         * 驱动地址
         * */
        private String url;

        /**
         * 用户名
         * */
        private String username;

        /**
         * 密码
         * */
        private String password;

        /**
         * 保持连接时常 单位秒
         * */
        private Integer keepAlive = 30;

        /**
         * 连接超时 单位秒
         * */
        private Integer connectionTimeout = 30;

        /**
         * 客户端id前缀
         * */
        private String clientIdPrefix;

        /**
         * 发布默认配置
         * */
        private Publish publish;

        /**
         * 订阅列表
         * */
        private Subscribe subscribe;
    }

    @Data
    public static class Subscribe{

        /**
         * 通道名称
         * */
        private String channelName;

        /**
         *
         * */
        private List<Topic> topics;
    }

    @Data
    public static class Publish{

        /**
         * 通道名称
         * */
        private String channelName;

        /**
         * 默认主题
         * */
        private String defaultTopic;

        /**
         * 消息等级
         * */
        private Integer defaultQos = 0;

        /**
         * 是否发送异步
         * */
        private Boolean async = true;

        /**
         * 是否保留最后一条消息
         * */
        private Boolean defaultRetained = false;
    }

    @Data
    public static class Topic{
        /**
         * 主题名
         * */
        private String topic;

        /**
         * 消息等级
         * */
        private Integer qos = 0;
    }
}
