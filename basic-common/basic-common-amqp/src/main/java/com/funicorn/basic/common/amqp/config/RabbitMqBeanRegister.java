package com.funicorn.basic.common.amqp.config;

import com.funicorn.basic.common.amqp.annotation.RabbitMQRegister;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * RabbitMqBeanRegister自动注册
 * @author Aimee
 * @since 2021/10/8 8:47
 */
@Slf4j
@DependsOn("rabbitListenerContainerFactory")
@Component
public class RabbitMqBeanRegister implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @SneakyThrows
    @PostConstruct
    public void init () {
        Map<String, Object> mqReceiverMap;
        try {
            mqReceiverMap = applicationContext.getBeansWithAnnotation(RabbitMQRegister.class);
        } catch (BeansException e) {
            log.warn("no BeansWithAnnotation RabbitMQRegister.class");
            return;
        }
        if (mqReceiverMap.isEmpty()) {
            return;
        }

        List<String> initList = new ArrayList<>();

        ConfigurableApplicationContext configApp = (ConfigurableApplicationContext) applicationContext;

        BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) configApp.getBeanFactory();

        int i = 1;
        for (Map.Entry<String, Object> entry : mqReceiverMap.entrySet()) {
            RabbitMQRegister rabbitMqRegister = entry.getValue().getClass().getAnnotation(RabbitMQRegister.class);
            if (rabbitMqRegister == null) {
                log.warn(entry.getValue().getClass().getName() + "上没有添加RabbitMQTopic注解");
                continue;
            }

            if (initList.contains(rabbitMqRegister.queue())) {
                log.warn("[{}]已完成初始化，不再重新加载!", rabbitMqRegister.queue());
                continue;
            }

            //TopicExchange
            BeanDefinitionBuilder topicExchangeBeanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(TopicExchange.class);
            topicExchangeBeanDefinitionBuilder.addConstructorArgValue(rabbitMqRegister.exchange());
            topicExchangeBeanDefinitionBuilder.addConstructorArgValue(true);
            topicExchangeBeanDefinitionBuilder.addConstructorArgValue(false);
            beanFactory.registerBeanDefinition("exchange" + i, topicExchangeBeanDefinitionBuilder.getRawBeanDefinition());

            //Queue
            BeanDefinitionBuilder queueBeanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(Queue.class);
            queueBeanDefinitionBuilder.addConstructorArgValue(rabbitMqRegister.queue());
            queueBeanDefinitionBuilder.addConstructorArgValue(true);
            queueBeanDefinitionBuilder.addConstructorArgValue(false);
            queueBeanDefinitionBuilder.addConstructorArgValue(false);
            beanFactory.registerBeanDefinition("queue" + i, queueBeanDefinitionBuilder.getRawBeanDefinition());
            //Binding
            BeanDefinitionBuilder bindBeanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(Binding.class);
            bindBeanDefinitionBuilder.addConstructorArgValue(rabbitMqRegister.queue());
            bindBeanDefinitionBuilder.addConstructorArgValue(Binding.DestinationType.QUEUE);
            bindBeanDefinitionBuilder.addConstructorArgValue(rabbitMqRegister.exchange());
            bindBeanDefinitionBuilder.addConstructorArgValue(rabbitMqRegister.routing());
            bindBeanDefinitionBuilder.addConstructorArgValue(null);
            beanFactory.registerBeanDefinition("bind" + i, bindBeanDefinitionBuilder.getRawBeanDefinition());

            initList.add(rabbitMqRegister.queue());
            i++;
            log.info(">>>>>>>>>> RabbitMQ queue:[{}] 注册成功", rabbitMqRegister.queue());
        }
    }
}
