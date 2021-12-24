package com.funicorn.basic.common.amqp.config;

import com.funicorn.basic.common.amqp.property.RabbitMqProperties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * RabbitMqBeanRegister自动注册
 * @author Aimee
 * @since 2021/10/8 8:47
 */
@Slf4j
@DependsOn("rabbitListenerContainerFactory")
@Component
@EnableConfigurationProperties(RabbitMqProperties.class)
public class AmqpBeanRegisterConfiguration implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Resource
    private RabbitMqProperties rabbitMqProperties;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @SneakyThrows
    @PostConstruct
    public void init () {
        if (rabbitMqProperties==null || rabbitMqProperties.getConsumers().isEmpty()) {
            return;
        }
        List<String> initList = new ArrayList<>();
        ConfigurableApplicationContext configApp = (ConfigurableApplicationContext) applicationContext;
        BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) configApp.getBeanFactory();
        int i = 1;
        for (RabbitMqProperties.Topic topic : rabbitMqProperties.getConsumers()) {
            if (StringUtils.isBlank(topic.getQueue()) || StringUtils.isBlank(topic.getExchange()) || StringUtils.isBlank(topic.getRouting())) {
                throw new IllegalArgumentException("[" + topic.toString() + "]配置不合法!");
            }
            if (initList.contains(topic.getQueue())) {
                continue;
            }
            //TopicExchange
            BeanDefinitionBuilder topicExchangeBeanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(TopicExchange.class);
            topicExchangeBeanDefinitionBuilder.addConstructorArgValue(topic.getExchange());
            topicExchangeBeanDefinitionBuilder.addConstructorArgValue(true);
            topicExchangeBeanDefinitionBuilder.addConstructorArgValue(false);
            beanFactory.registerBeanDefinition("exchange" + i, topicExchangeBeanDefinitionBuilder.getRawBeanDefinition());
            //Queue
            BeanDefinitionBuilder queueBeanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(Queue.class);
            queueBeanDefinitionBuilder.addConstructorArgValue(topic.getQueue());
            queueBeanDefinitionBuilder.addConstructorArgValue(true);
            queueBeanDefinitionBuilder.addConstructorArgValue(false);
            queueBeanDefinitionBuilder.addConstructorArgValue(false);
            beanFactory.registerBeanDefinition("queue" + i, queueBeanDefinitionBuilder.getRawBeanDefinition());
            //Binding
            BeanDefinitionBuilder bindBeanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(Binding.class);
            bindBeanDefinitionBuilder.addConstructorArgValue(topic.getQueue());
            bindBeanDefinitionBuilder.addConstructorArgValue(Binding.DestinationType.QUEUE);
            bindBeanDefinitionBuilder.addConstructorArgValue(topic.getExchange());
            bindBeanDefinitionBuilder.addConstructorArgValue(topic.getRouting());
            bindBeanDefinitionBuilder.addConstructorArgValue(null);
            beanFactory.registerBeanDefinition("bind" + i, bindBeanDefinitionBuilder.getRawBeanDefinition());
            initList.add(topic.getQueue());
            i++;
            log.info("------> RabbitMQ queue:[{}] register success.", topic.getQueue());
        }
    }
}
