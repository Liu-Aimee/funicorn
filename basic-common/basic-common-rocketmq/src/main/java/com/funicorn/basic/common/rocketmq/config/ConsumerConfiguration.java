package com.funicorn.basic.common.rocketmq.config;

import com.funicorn.basic.common.rocketmq.processor.ConsumeMsgListener;
import com.funicorn.basic.common.rocketmq.property.RocketMqProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Aimee
 * @since 2021/12/22 14:44
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({RocketMqProperties.class})
@Import(ConsumeMsgListener.class)
public class ConsumerConfiguration {

    @Resource
    private RocketMqProperties rocketMqProperties;
    @Resource
    private ConsumeMsgListener consumeMsgListener;

    /**
     * mq 消费者配置
     */
    @ConditionalOnBean({RocketMqProperties.class,ConsumeMsgListener.class})
    @Bean
    public DefaultMQPushConsumer defaultConsumer() throws Exception{
        log.info("------>RocketMQ Consumer init...<------");
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(rocketMqProperties.getConsumer().getGroupName());
        consumer.setNamesrvAddr(rocketMqProperties.getNameServer());
        consumer.setConsumeThreadMin(rocketMqProperties.getConsumer().getConsumeThreadMin());
        consumer.setConsumeThreadMax(rocketMqProperties.getConsumer().getConsumeThreadMax());
        consumer.setConsumeMessageBatchMaxSize(rocketMqProperties.getConsumer().getConsumeMessageBatchMaxSize());
        // 设置监听
        consumer.registerMessageListener(consumeMsgListener);

        /*
         * 设置consumer第一次启动是从队列头部开始还是队列尾部开始
         * 如果不是第一次启动，那么按照上次消费的位置继续消费
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        /*
         * 设置消费模型，集群还是广播，默认为集群
         * 广播：一条消息被多个consumer消费
         * 集群：一个ConsumerGroup中的Consumer实例平均分摊消费消息
         */
        consumer.setMessageModel(MessageModel.CLUSTERING);

        // 设置该消费者订阅的主题和tag，如果订阅该主题下的所有tag，则使用*,
        List<String> topicTags = rocketMqProperties.getConsumer().getTopicTags();
        if (topicTags==null) {
            throw new RuntimeException("未配置订阅主题!");
        }

        for (String topicTag : topicTags) {
            String[] topicArr = topicTag.split(":");
            if (topicArr.length<=0) {
                throw new RuntimeException("未配置订阅主题!");
            }
            if (topicArr.length==1) {
                consumer.subscribe(topicArr[0], "*");
            } else {
                String[] tags = topicArr[1].split(";");
                for (String tag : tags) {
                    consumer.subscribe(topicArr[0], tag);
                }
            }
        }
        consumer.start();
        log.info("------>RocketMQ consumer nameSrvAddr:{}",rocketMqProperties.getNameServer());
        log.info("------>RocketMQ consumer groupName:{}",rocketMqProperties.getConsumer().getGroupName());
        log.info("------>RocketMQ consumer started.<------");
        return consumer;
    }
}
