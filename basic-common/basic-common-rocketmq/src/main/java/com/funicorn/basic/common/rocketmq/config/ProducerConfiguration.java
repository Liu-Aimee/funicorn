package com.funicorn.basic.common.rocketmq.config;

import com.funicorn.basic.common.rocketmq.property.RocketMqProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author Aimee
 * @since 2021/12/22 14:42
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({RocketMqProperties.class})
public class ProducerConfiguration {

    @Resource
    private RocketMqProperties rocketMqProperties;

    /**
     * mq 生成者配置
     * @throws MQClientException 异常
     * @return DefaultMQProducer
     */
    @ConditionalOnBean(RocketMqProperties.class)
    @Bean
    public DefaultMQProducer defaultMQProducer() throws MQClientException {
        log.info("------>RocketMQ producer init...<------");
        DefaultMQProducer producer = new DefaultMQProducer(rocketMqProperties.getProducer().getGroupName());
        producer.setNamesrvAddr(rocketMqProperties.getNameServer());
        producer.setVipChannelEnabled(false);
        producer.setMaxMessageSize(rocketMqProperties.getProducer().getMaxMessageSize());
        producer.setSendMsgTimeout(rocketMqProperties.getProducer().getSendMsgTimeOut());
        producer.setRetryTimesWhenSendAsyncFailed(rocketMqProperties.getProducer().getRetryTimesWhenSendFailed());
        producer.start();
        log.info("------>RocketMQ producer groupName:{}",rocketMqProperties.getProducer().getGroupName());
        log.info("------>RocketMQ producer nameSrvAddr:{}",rocketMqProperties.getNameServer());
        log.info("------>RocketMQ producer maxMessageSize:{}M",rocketMqProperties.getProducer().getMaxMessageSize()/1024);
        log.info("------>RocketMQ producer sendMsgTimeOut:{}ms",rocketMqProperties.getProducer().getSendMsgTimeOut());
        log.info("------>RocketMQ producer server started.<------");
        return producer;
    }
}
