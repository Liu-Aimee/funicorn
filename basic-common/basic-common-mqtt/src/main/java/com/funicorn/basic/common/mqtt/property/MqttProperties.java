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

    private List<Driver> drivers;

    @Data
    public static class Driver{

        private String url;

        private String username;

        private String password;

        private Integer keepAlive;

        private Integer connectionTimeout = 30;

        private String clientId;

        private List<Topic> topics;
    }

    @Data
    public static class Topic{

        /**
         * subscribe/publish
         * */
        private String model = "subscribe";

        private String topicName;

        private Integer qos = 0;

        private boolean async = true;

        private boolean retained = false;
    }
}
