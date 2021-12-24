package com.funicorn.basic.common.base.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Aimee
 * @since 2021/10/27 9:33
 */
@Configuration
@ConfigurationProperties(prefix = "funicorn.config")
@Data
public class FunicornConfigProperties {

    /**
     * 白名单
     * */
    private List<String> whiteUrls;

    /**
     * 权限校验配置
     * */
    private Security security;

    /**
     * 权限校验配置类
     * */
    @Data
    public static class Security {

        /**
         * 客户端id
         * */
        private String clientId;

        /**
         * 客户端密钥
         * */
        private String clientSecret;

        /**
         * 回调地址
         * */
        private String redirectUri;

        /**
         *
         * */
        private String grantType;

        /**
         *
         * */
        private String responseType;

        /**
         * 作用域
         * */
        private String scope;

        /**
         * 认证服务地址
         * */
        private String serverAddr;

        /**
         * 统一登录页面地址
         * */
        private String loginUrl;

        /**
         * 登录接口
         * */
        private String loginApi;
        /**
         * 登出接口
         * */
        private String logoutApi;

        /**
         * token校验地址
         * */
        private String checkTokenApi;

        /**
         * token解析地址
         * */
        private String tokenApi;
    }
}
