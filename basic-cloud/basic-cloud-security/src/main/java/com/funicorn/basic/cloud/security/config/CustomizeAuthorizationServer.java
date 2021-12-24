package com.funicorn.basic.cloud.security.config;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 授权服务器配置
 * @author Aimee
 * @since 2020/6/29 8:40
 */
@Configuration
@EnableAuthorizationServer
public class CustomizeAuthorizationServer extends AuthorizationServerConfigurerAdapter {

    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private DataSource dataSource;
    @Resource
    private TokenStore tokenStore;
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private RedisAuthCodeConfig redisAuthCodeConfig;
    @Resource
    private TokenEnhancer tokenEnhancer;

    @Bean
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }

    /**
     * 安全约束
     * 允许表单形式的认证
     * 校验token需要认证
     * */
    @Override
    @SneakyThrows
    public void configure(AuthorizationServerSecurityConfigurer security){
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();
    }

    /**
    * 认证客户端信息配置
    * */
    @Override
    @SneakyThrows
    public void configure(ClientDetailsServiceConfigurer clients) {
        //从数据库中读取客户端详细信息
        clients.withClientDetails(clientDetails());
    }

    /**
    * 配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)
    * */
    @Override
    @SneakyThrows
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(authenticationManager);
        endpoints.tokenStore(tokenStore);
        endpoints.userDetailsService(userDetailsService);
        endpoints.authorizationCodeServices(redisAuthCodeConfig);
        //允许 GET、POST请求获取 token
        endpoints.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
        //Token增强器
        endpoints.tokenEnhancer(tokenEnhancer);
    }
}
