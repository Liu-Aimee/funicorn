package com.funicorn.basic.cloud.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author Aimee
 * @since 2020/6/30 15:05
 */
@Slf4j
@Component
public class RedisAuthCodeConfig extends RandomValueAuthorizationCodeServices {

    private static final String AUTH_CODE_KEY = "auth_code";
    @Resource
    RedisTemplate<String,Object> redisTemplate;


    @Override
    protected void store(String code, OAuth2Authentication authentication) {
        redisTemplate.execute((RedisCallback<Long>) connection -> {
            connection.set(codeKey(code).getBytes(), SerializationUtils.serialize(authentication),
                    Expiration.from(10, TimeUnit.MINUTES), RedisStringCommands.SetOption.UPSERT);
            return 1L;
        });
    }

    @Override
    protected OAuth2Authentication remove(String code) {
        return redisTemplate.execute((RedisCallback<OAuth2Authentication>) connection -> {
            byte[] keyByte = codeKey(code).getBytes();
            byte[] valueByte = connection.get(keyByte);

            if (valueByte != null) {
                connection.del(keyByte);
                return SerializationUtils.deserialize(valueByte);
            }

            return null;
        });
    }

    private String codeKey(String code) {
        return AUTH_CODE_KEY+":" + code;
    }
}
