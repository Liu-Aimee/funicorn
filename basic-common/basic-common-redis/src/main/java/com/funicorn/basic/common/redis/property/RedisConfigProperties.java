package com.funicorn.basic.common.redis.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Aimee
 * @since 2021/4/21 17:35
 */
@Configuration
@ConfigurationProperties(prefix = "funicorn.config.redis")
@Data
public class RedisConfigProperties {

    /**
     * 模式 standalone/cluster/sentinel
     * */
    private String model;

    /**
     * 单机版节点 ip:port
     * */
    private String address;

    /**
     * 密码
     * */
    private String password;

    /**
     * 数据库
     * */
    private Integer database;

    /**
     * 最大空闲数
     * */
    private Integer maxIdle;

    /**
     * 最小空闲数
     * */
    private Integer minIdle;

    /**
     * 最大连接数
     * */
    private Integer maxTotal;

    /**
     * 最大等待时间
     * */
    private Integer maxWaitMillis;

    /**
     * 集群
     * */
    private Cluster cluster;

    /**
     * 哨兵
     * */
    private Sentinel sentinel;

    /**
     * 集群配置类
     * */
    @Data
    public static class Cluster {
        /**
         * （从节点连接池大小） 默认值：64
         * */
        private Integer masterConnectionPoolSize;

        /**
         * 主节点连接池大小）默认值：64
         * */
        private Integer slaveConnectionPoolSize;

        /**
         * 节点
         * */
        private List<String> nodes;
    }

    /**
     * 哨兵配置类
     * */
    @Data
    public static class Sentinel {

        /**
         * 主节点名称
         * */
        private String master;
        /**
         * （从节点连接池大小） 默认值：64
         * */
        private Integer masterConnectionPoolSize;

        /**
         * 主节点连接池大小）默认值：64
         * */
        private Integer slaveConnectionPoolSize;
        /**
         * 节点数量
         * */
        private List<String> nodes;
    }
}
