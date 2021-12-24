package com.funicorn.basic.common.timer.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Aimee
 * @since 2021/9/25 9:29
 */
@Configuration
@ConfigurationProperties(prefix = "xxl.job")
@Data
@SuppressWarnings("all")
public class XxlJobProperties {

    /**注册中心配置*/
    private Admin admin;

    /**执行器配置*/
    private Executor executor;


    @Data
    public static class Admin {

        /**注册中心地址*/
        private String addresses;
    }

    @Data
    public static class Executor {

        /**执行器名称*/
        private String appname;
        /**执行器地址*/
        private String address;
        /**ip*/
        private String ip;
        /**端口*/
        private Integer port;
        /**日志保存地址*/
        private String logpath;
        /**日志保存天数*/
        private Integer logretentiondays;
        /**令牌*/
        private String accessToken;
    }
}
