package com.funicorn.cloud.system.center.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Aimee
 * @since 2021/11/9 9:39
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "oss")
public class OssProperties {

    /**
     * 域名端点
     * */
    private String endpoint;

    /**
     * 区域
     */
    private String region;

    /**
     * 签名
     * */
    private String accessKey;

    /**
     * 密钥
     * */
    private String secretKey;

    /**
     * 桶名称
     * */
    private String bucketName;

    /**
     * 路径访问
     * */
    private Boolean pathStyleAccess = true;

    /**
     * 最大线程数，默认： 100
     */
    private Integer maxConnections = 100;
}
