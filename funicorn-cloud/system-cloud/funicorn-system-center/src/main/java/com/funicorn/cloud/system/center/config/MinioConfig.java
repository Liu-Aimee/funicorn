package com.funicorn.cloud.system.center.config;

import com.funicorn.cloud.system.center.property.OssProperties;
import io.minio.MinioClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author Aimee
 * @since 2021/11/9 9:38
 */
@Configuration
@ConditionalOnBean(OssProperties.class)
public class MinioConfig {

    @Resource
    private OssProperties ossProperties;

    /**
     * 初始化minio
     * */
    @Bean
    public MinioClient minioClient(){
        MinioClient.Builder builder = MinioClient.builder().endpoint(ossProperties.getEndpoint()).credentials(ossProperties.getAccessKey(), ossProperties.getSecretKey());
        if (StringUtils.isNotBlank(ossProperties.getRegion())) {
            builder.region(ossProperties.getRegion());
        }
        return builder.build();
    }
}
