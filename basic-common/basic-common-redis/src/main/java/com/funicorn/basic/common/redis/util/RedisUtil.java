package com.funicorn.basic.common.redis.util;

import com.funicorn.basic.common.base.util.JsonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Aimee
 * @since 2021/10/28 8:48
 */
@Component
@SuppressWarnings("unused")
public class RedisUtil {

    /**
     * redis key前缀
     * 防止多项目共用redis时key重复
     * */
    @Value("${spring.application.name}")
    private String prefix;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * redis新增缓存
     * @param key key
     * @param value value
     * */
    public void setStringValue(String key,Object value){
        redisTemplate.opsForValue().set(prefix + ":" + key, value);
    }

    /**
     * redis新增缓存
     * @param key key
     * @param value value
     * @param timeout 过期时间
     * @param unit TimeUnit
     * */
    public void setStringValue(String key, Object value, long timeout, TimeUnit unit){
        redisTemplate.opsForValue().set(prefix + ":" + key, value,timeout,unit);
    }

    /**
     * 根据key获取缓存数据
     * @param key key
     * @param <T> 泛型
     * @param clazz Class转换对象
     * @return T 返回对象实例
     * */
    public <T> T getStringValue(String key,Class<T> clazz){
        Object object = redisTemplate.opsForValue().get(prefix + ":" + key);
        if (object==null){
            return null;
        } else {
            return JsonUtil.object2Object(object,clazz);
        }
    }

    /**
     * redis新增缓存
     * @param redisKey key
     * @param hashKey hashKey
     * @param hashValue hashValue
     * */
    public void putHashValue(String redisKey,String hashKey,Object hashValue){
        redisTemplate.opsForHash().put(prefix + ":" +redisKey,hashKey,hashValue);
    }

    /**
     * redis新增缓存
     * @param redisKey key
     * @param valueMap valueMap
     * */
    public void putAllHashValue(String redisKey, Map<?,?> valueMap){
        redisTemplate.opsForHash().putAll(prefix + ":" +redisKey,valueMap);
    }

    /**
     * 删除hashKey
     * @param redisKey redisKey
     * @param  hashKeys hashKeys
     * @return Long 删除数量
     * */
    public Long deleteHashKey(String redisKey,Object... hashKeys){
        return redisTemplate.opsForHash().delete(prefix + ":" + redisKey,hashKeys);
    }

    /**
     * 获取hashKey对应的缓存数据
     * @param redisKey redisKey
     * @param hashKey hashKey
     * @param clazz clazz
     * @param <T> 泛型
     * @return T 转换后的对象
     * */
    public <T> T getHashValue(String redisKey,Object hashKey,Class<T> clazz){
        Object object = redisTemplate.opsForHash().get(prefix + ":" + redisKey,hashKey);
        if (object==null){
            return null;
        }else {
            return JsonUtil.object2Object(object,clazz);
        }
    }

    /**
     * 批量获取hashKey对应的缓存数据
     * @param redisKey redisKey
     * @param hashKeys hashKeys
     * @return List
     * */
    public List<Object> multiGetHashValue(String redisKey, Set<Object> hashKeys){
        return redisTemplate.opsForHash().multiGet(prefix + ":" + redisKey,hashKeys);
    }

    /**
     * 是否存在某个hashKey
     * @param redisKey redisKey
     * @param hashKey hashKey
     * @return true false
     * */
    public boolean hasHashKey(String redisKey,String hashKey){
        return redisTemplate.opsForHash().hasKey(prefix + ":" + redisKey,hashKey);
    }

    /**
     * 删除redisKey
     * @param key key
     * @return true false
     * */
    public boolean delete(String key){
        Boolean success = redisTemplate.delete(prefix + ":" + key);
        return success == null || success;
    }
}
