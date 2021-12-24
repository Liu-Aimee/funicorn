package com.funicorn.basic.common.timer.config;

import com.funicorn.basic.common.timer.property.XxlJobProperties;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Aimee
 * @since 2021/9/25 9:29
 */
@Slf4j
@Component
@EnableConfigurationProperties(XxlJobProperties.class)
public class XxlJobConfig {

    @Resource
    private XxlJobProperties xxlJobProperties;

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        log.info(">>>>>>>>>>> xxl-job config init.");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(xxlJobProperties.getAdmin().getAddresses());
        if (StringUtils.isNotBlank(xxlJobProperties.getExecutor().getAppname())){
            xxlJobSpringExecutor.setAppname(xxlJobProperties.getExecutor().getAppname());
        }
        if (StringUtils.isNotBlank(xxlJobProperties.getExecutor().getIp())){
            xxlJobSpringExecutor.setIp(xxlJobProperties.getExecutor().getIp());
        }

        if (xxlJobProperties.getExecutor().getPort()!=null){
            xxlJobSpringExecutor.setPort(xxlJobProperties.getExecutor().getPort());
        }

        if (StringUtils.isNotBlank(xxlJobProperties.getExecutor().getAccessToken())){
            xxlJobSpringExecutor.setAccessToken(xxlJobProperties.getExecutor().getAccessToken());
        }

        xxlJobSpringExecutor.setLogPath(xxlJobProperties.getExecutor().getLogpath());
        xxlJobSpringExecutor.setLogRetentionDays(xxlJobProperties.getExecutor().getLogretentiondays());
        return xxlJobSpringExecutor;
    }
}
