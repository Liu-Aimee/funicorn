package com.funicorn.basic.common.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.funicorn.basic.common.base.constant.BaseConstant;
import com.funicorn.basic.common.base.exception.BaseErrorCode;
import com.funicorn.basic.common.base.exception.CommonBaseException;
import com.funicorn.basic.common.redis.property.RedisConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration.builder;

/**
 * @author Aimee
 * @since 2021/4/21 17:34
 */
@Configuration
@Slf4j
@ConditionalOnBean(RedisConfigProperties.class)
public class RedisConfig {

    @Resource
    private RedisConfigProperties redisConfigProperties;

    /**
     * redis最小集群数量
     * */
    private static final Integer MIN_REDIS_CLUSTER_NUM = 6;

    /**
     * redis最小哨兵数量
     * */
    private static final Integer MIN_REDIS_SENTINEL_NUM = 3;

    @Bean
    @Primary
    public LettuceConnectionFactory lettuceConnectionFactory(){
        // 连接池配置
        GenericObjectPoolConfig<?> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxIdle(redisConfigProperties.getMaxIdle() == null ? 8 : redisConfigProperties.getMaxIdle());
        poolConfig.setMinIdle(redisConfigProperties.getMinIdle() == null ? 1 : redisConfigProperties.getMinIdle());
        poolConfig.setMaxTotal(redisConfigProperties.getMaxTotal() == null ? -1 : redisConfigProperties.getMaxTotal());
        poolConfig.setMaxWaitMillis(redisConfigProperties.getMaxWaitMillis() == null ? 10000L : redisConfigProperties.getMaxWaitMillis());
        LettucePoolingClientConfiguration lettucePoolingClientConfiguration = builder().poolConfig(poolConfig).build();

        //单机模式
        if (StringUtils.isBlank(redisConfigProperties.getModel()) || BaseConstant.REDIS_MODEL_STANDALONE.equals(redisConfigProperties.getModel())){
            return initStandalone(lettucePoolingClientConfiguration);
        }

        // 集群版本
        if (BaseConstant.REDIS_MODEL_CLUSTER.equals(redisConfigProperties.getModel())){
            return initCluster(lettucePoolingClientConfiguration);
        }

        //哨兵模式
        if (BaseConstant.REDIS_MODEL_SENTINEL.equals(redisConfigProperties.getModel())){
            return initSentinel(lettucePoolingClientConfiguration);
        }
        return null;
    }

    /**
     * 初始化单机版redis配置
     * @param lettucePoolingClientConfiguration lettucePoolingClientConfiguration
     * @return LettuceConnectionFactory
     * */
    private LettuceConnectionFactory initStandalone(LettucePoolingClientConfiguration lettucePoolingClientConfiguration){
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        String[] childHosts = redisConfigProperties.getAddress().split(":");
        if (childHosts.length < 2){
            throw new CommonBaseException(BaseErrorCode.REDIS_ONE_NODE_ERROR,redisConfigProperties.getAddress());
        }
        redisConfig.setHostName(childHosts[0]);
        redisConfig.setPort(Integer.parseInt(childHosts[1]));
        redisConfig.setDatabase(redisConfigProperties.getDatabase());
        if (StringUtils.isNoneBlank(redisConfigProperties.getPassword())) {
            redisConfig.setPassword(redisConfigProperties.getPassword());
        }
        return new LettuceConnectionFactory(redisConfig, lettucePoolingClientConfiguration);
    }

    /**
     * 初始化集群版redis配置
     * @param lettucePoolingClientConfiguration lettucePoolingClientConfiguration
     * @return LettuceConnectionFactory
     * */
    private LettuceConnectionFactory initCluster(LettucePoolingClientConfiguration lettucePoolingClientConfiguration){
        if (redisConfigProperties.getCluster()==null || redisConfigProperties.getCluster().getNodes()==null
                || redisConfigProperties.getCluster().getNodes().isEmpty() || redisConfigProperties.getCluster().getNodes().size() < MIN_REDIS_CLUSTER_NUM){
            throw new CommonBaseException(BaseErrorCode.REDIS_NODES_ERROR);
        }

        RedisClusterConfiguration redisConfig = new RedisClusterConfiguration();
        Set<RedisNode> redisNodes = new HashSet<>();
        for (String serverAddr:redisConfigProperties.getCluster().getNodes()) {
            if (StringUtils.isNotBlank(serverAddr)) {
                String[] childHosts = serverAddr.split(":");
                if (childHosts.length<2){
                    throw new CommonBaseException(BaseErrorCode.REDIS_ONE_NODE_ERROR,serverAddr);
                }
                int port = Integer.parseInt(childHosts[1]);
                redisNodes.add(new RedisNode(childHosts[0], port));
            }
        }

        redisConfig.setClusterNodes(redisNodes);
        // 跨集群执行命令时要遵循的最大重定向数量
        redisConfig.setMaxRedirects(3);
        if (StringUtils.isNoneBlank(redisConfigProperties.getPassword())) {
            redisConfig.setPassword(redisConfigProperties.getPassword());
        }
        return new LettuceConnectionFactory(redisConfig, lettucePoolingClientConfiguration);
    }

    /**
     * 初始化哨兵版redis配置
     * @param lettucePoolingClientConfiguration lettucePoolingClientConfiguration
     * @return LettuceConnectionFactory
     * */
    private LettuceConnectionFactory initSentinel(LettucePoolingClientConfiguration lettucePoolingClientConfiguration){
        if (redisConfigProperties.getSentinel()==null || StringUtils.isBlank(redisConfigProperties.getSentinel().getMaster())){
            throw new CommonBaseException(BaseErrorCode.REDIS_MASTER_NAME_ERROR);
        }

        if (redisConfigProperties.getSentinel()==null || redisConfigProperties.getSentinel().getNodes()==null
                || redisConfigProperties.getSentinel().getNodes().isEmpty() || redisConfigProperties.getSentinel().getNodes().size()<MIN_REDIS_SENTINEL_NUM){
            throw new CommonBaseException(BaseErrorCode.REDIS_NODES_ERROR);
        }

        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
        redisSentinelConfiguration.setMaster(redisConfigProperties.getSentinel().getMaster());
        Set<RedisNode> redisNodes = new HashSet<>();
        for (String serverAddr:redisConfigProperties.getSentinel().getNodes()) {
            if (StringUtils.isNotBlank(serverAddr)) {
                String[] childHosts = serverAddr.split(":");
                if (childHosts.length< 2 ){
                    throw new CommonBaseException(BaseErrorCode.REDIS_ONE_NODE_ERROR,serverAddr);
                }
                int port = Integer.parseInt(childHosts[1]);
                redisNodes.add(new RedisNode(childHosts[0], port));
            }
        }
        redisSentinelConfiguration.setSentinels(redisNodes);
        if (StringUtils.isNoneBlank(redisConfigProperties.getPassword())) {
            redisSentinelConfiguration.setPassword(redisConfigProperties.getPassword());
        }
        return new LettuceConnectionFactory(redisSentinelConfiguration, lettucePoolingClientConfiguration);
    }

    /**
     * RedisTemplate配置
     * @param lettuceConnectionFactory 连接工厂
     * @return RedisTemplate
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        // 设置序列化
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);

        //LocalDatetime序列化
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        timeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        om.registerModule(timeModule);

        jackson2JsonRedisSerializer.setObjectMapper(om);
        // 配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        RedisSerializer<?> stringSerializer = new StringRedisSerializer();
        // key序列化
        redisTemplate.setKeySerializer(stringSerializer);
        // value序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // Hash key序列化
        redisTemplate.setHashKeySerializer(stringSerializer);
        // Hash value序列化
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
