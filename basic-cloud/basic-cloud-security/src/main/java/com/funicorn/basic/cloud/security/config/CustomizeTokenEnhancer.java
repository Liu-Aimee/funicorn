package com.funicorn.basic.cloud.security.config;

import com.funicorn.basic.cloud.security.model.CurrentUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * token增强器
 * @author Aimee
 * @since 2021/11/1 16:44
 */
@Configuration
@Primary
public class CustomizeTokenEnhancer implements TokenEnhancer {

    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * Token发布商店
     * jwt模式 return new JwtTokenStore(jwtAccessTokenConverter());
     * redis模式 return new RedisTokenStore(redisConnectionFactory);
     * @return TokenStore
     * */
    @Bean
    @Primary
    public TokenStore tokenStore(){
        return new RedisTokenStore(redisConnectionFactory);
    }

    /**
     * token数据增强 添加附加信息
     * @param accessToken accessToken
     * @param authentication authentication
     * @return OAuth2AccessToken
     * */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();
        Map<String, Object> map = new HashMap<>(2);
        currentUser.setPassword(null);
        map.put("currentUser",currentUser);
        //设置附加信息
        ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(map);
        return accessToken;
    }
}
