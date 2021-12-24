package com.funicorn.basic.common.redis.config;

import com.funicorn.basic.common.base.constant.BaseConstant;
import com.funicorn.basic.common.base.exception.BaseErrorCode;
import com.funicorn.basic.common.base.exception.CommonBaseException;
import com.funicorn.basic.common.redis.property.RedisConfigProperties;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.config.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Aimee
 * @since 2021/4/21 17:36
 */
@Configuration
@ConditionalOnBean(RedisConfigProperties.class)
public class RedissonConfig {

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

    /**
     * redis://
     * */
    private static final String NODE_START_WITH = "redis://";
    @Bean
    public Redisson redisson() {

        /*
         * 单机版本
         */
        if (StringUtils.isBlank(redisConfigProperties.getModel()) || BaseConstant.REDIS_MODEL_STANDALONE.equals(redisConfigProperties.getModel())){
            return createSingleRedisson();
        }

        /*
         * 集群版本
         * */
        if (BaseConstant.REDIS_MODEL_CLUSTER.equals(redisConfigProperties.getModel())){
            return createClusterRedisson();
        }

        /*
        * 哨兵版本
        * */
        if (BaseConstant.REDIS_MODEL_SENTINEL.equals(redisConfigProperties.getModel())){
            return createSentinelRedisson();
        }

        return null;
    }

    /**
     * 单机版redisson
     * @return Redisson
     * */
    private Redisson createSingleRedisson(){
        Config config = new Config();
        String redisUrl;
        if (redisConfigProperties.getAddress().startsWith(NODE_START_WITH)){
            redisUrl =  redisConfigProperties.getAddress();
        } else {
            redisUrl =  NODE_START_WITH + redisConfigProperties.getAddress();
        }
        SingleServerConfig singleServerConfig = config.useSingleServer()
                .setAddress(redisUrl)
                .setConnectionPoolSize(redisConfigProperties.getMaxTotal())
                .setTimeout(redisConfigProperties.getMaxWaitMillis())
                .setConnectionMinimumIdleSize(redisConfigProperties.getMinIdle())
                .setDatabase(redisConfigProperties.getDatabase());
        //设置密码
        if (StringUtils.isNotBlank(redisConfigProperties.getPassword())){
            singleServerConfig.setPassword(redisConfigProperties.getPassword());
        }
        return (Redisson)Redisson.create(config);
    }

    /**
     * 集群版redisson
     * @return Redisson
     * */
    private Redisson createClusterRedisson(){
        if (redisConfigProperties.getCluster()==null || redisConfigProperties.getCluster().getNodes()==null
                || redisConfigProperties.getCluster().getNodes().isEmpty() || redisConfigProperties.getCluster().getNodes().size() < MIN_REDIS_CLUSTER_NUM){
            throw new CommonBaseException(BaseErrorCode.REDIS_NODES_ERROR);
        }
        List<String> clusterNodes = new ArrayList<>();
        for (int i = 0; i < redisConfigProperties.getCluster().getNodes().size(); i++) {
            if (redisConfigProperties.getCluster().getNodes().get(i).startsWith(NODE_START_WITH)){
                clusterNodes.add(redisConfigProperties.getCluster().getNodes().get(i));
            } else {
                clusterNodes.add(NODE_START_WITH + redisConfigProperties.getCluster().getNodes().get(i));
            }
        }
        Config config = new Config();
        ClusterServersConfig clusterServersConfig = config.useClusterServers()
                .addNodeAddress(clusterNodes.toArray(new String[0]))
                .setTimeout(redisConfigProperties.getMaxWaitMillis())
                .setReadMode(ReadMode.SLAVE)
                .setMasterConnectionPoolSize(redisConfigProperties.getCluster().getMasterConnectionPoolSize()!=null ? redisConfigProperties.getCluster().getMasterConnectionPoolSize() : 64)
                .setSlaveConnectionPoolSize(redisConfigProperties.getCluster().getSlaveConnectionPoolSize()!=null ? redisConfigProperties.getCluster().getSlaveConnectionPoolSize() : 64);

        //设置密码
        if (StringUtils.isNotBlank(redisConfigProperties.getPassword())){
            clusterServersConfig.setPassword(redisConfigProperties.getPassword());
        }

        return (Redisson) Redisson.create(config);
    }

    /**
     * 哨兵版redisson
     * @return Redisson
     * */
    private Redisson createSentinelRedisson(){
        if (redisConfigProperties.getSentinel()==null || StringUtils.isBlank(redisConfigProperties.getSentinel().getMaster())){
            throw new CommonBaseException(BaseErrorCode.REDIS_MASTER_NAME_ERROR);
        }

        if (redisConfigProperties.getSentinel()==null || redisConfigProperties.getSentinel().getNodes()==null
                || redisConfigProperties.getSentinel().getNodes().isEmpty() || redisConfigProperties.getSentinel().getNodes().size()<MIN_REDIS_SENTINEL_NUM){
            throw new CommonBaseException(BaseErrorCode.REDIS_NODES_ERROR);
        }

        List<String> sentinelNodes = new ArrayList<>();
        for (int i = 0; i < redisConfigProperties.getSentinel().getNodes().size(); i++) {
            if (redisConfigProperties.getSentinel().getNodes().get(i).startsWith(NODE_START_WITH)){
                sentinelNodes.add(redisConfigProperties.getSentinel().getNodes().get(i));
            } else {
                sentinelNodes.add(NODE_START_WITH + redisConfigProperties.getSentinel().getNodes().get(i));
            }
        }
        Config config = new Config();
        SentinelServersConfig sentinelServersConfig  = config.useSentinelServers()
                .addSentinelAddress(sentinelNodes.toArray(new String[0]))
                .setMasterName(redisConfigProperties.getSentinel().getMaster())
                .setReadMode(ReadMode.SLAVE)
                .setDatabase(redisConfigProperties.getDatabase())
                .setTimeout(redisConfigProperties.getMaxWaitMillis())
                .setReadMode(ReadMode.SLAVE)
                .setMasterConnectionPoolSize(redisConfigProperties.getSentinel().getMasterConnectionPoolSize()!=null ? redisConfigProperties.getSentinel().getMasterConnectionPoolSize() : 64)
                .setSlaveConnectionPoolSize(redisConfigProperties.getSentinel().getSlaveConnectionPoolSize()!=null ? redisConfigProperties.getSentinel().getSlaveConnectionPoolSize() : 64);

        if (StringUtils.isNotBlank(redisConfigProperties.getPassword())) {
            sentinelServersConfig.setPassword(redisConfigProperties.getPassword());
        }
        return (Redisson) Redisson.create(config);
    }
}
