package com.funicorn.basic.common.amqp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * @author Aimee
 * @since 2021/9/30 17:17
 */
@Slf4j
@Configuration
@SuppressWarnings("all")
public class RabbitMqConfig {

    /**
     * rabbitMq实例
     * @param connectionFactory 工厂
     * @return RabbitTemplate
     * */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnsCallback((returnedMessage) ->
                log.error("Send message({}) to exchange({}), routingKey({}) failed: {}",
                        returnedMessage.getMessage(), returnedMessage.getExchange(),
                        returnedMessage.getRoutingKey(), returnedMessage.getReplyText()));
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                log.error("CorrelationData({}) ack failed: {}", correlationData, cause);
            }
        });
        return rabbitTemplate;
    }


    /**
     * rabbitMq消费者工厂
     * @param connectionFactory 工厂
     * @return RabbitListenerContainerFactory
     * */
    @Bean
    @DependsOn("rabbitTemplate")
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

    /**
     * Json解析器
     * @param objectMapper 工厂
     * @return MessageConverter
     * */
    @Bean
    @DependsOn("rabbitListenerContainerFactory")
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
