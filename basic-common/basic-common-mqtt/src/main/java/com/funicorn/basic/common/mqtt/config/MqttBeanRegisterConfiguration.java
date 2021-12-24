package com.funicorn.basic.common.mqtt.config;

import com.funicorn.basic.common.mqtt.property.MqttProperties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dispatcher.RoundRobinLoadBalancingStrategy;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * mqtt自动注册
 * @author Aimee
 * @since 2021/10/8 8:47
 */
@Slf4j
@Component
@EnableConfigurationProperties(MqttProperties.class)
public class MqttBeanRegisterConfiguration implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Resource
    private MqttProperties mqttProperties;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @SneakyThrows
    @PostConstruct
    public void init () {
        if (mqttProperties==null || mqttProperties.getDrivers()==null || mqttProperties.getDrivers().isEmpty()) {
            return;
        }

        List<String> initList = new ArrayList<>();
        ConfigurableApplicationContext configApp = (ConfigurableApplicationContext) applicationContext;
        BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) configApp.getBeanFactory();

        int i = 0;
        for (MqttProperties.Driver driver : mqttProperties.getDrivers()) {
            if (initList.contains(driver.getUrl())) {
                return;
            }
            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setUserName(driver.getUsername());
            mqttConnectOptions.setPassword(driver.getPassword().toCharArray());
            mqttConnectOptions.setServerURIs(new String[]{driver.getUrl()});
            mqttConnectOptions.setKeepAliveInterval(driver.getKeepAlive());
            mqttConnectOptions.setConnectionTimeout(driver.getConnectionTimeout());
            BeanDefinitionBuilder mqttFactoryBuilder = BeanDefinitionBuilder.genericBeanDefinition(DefaultMqttPahoClientFactory.class);
            mqttFactoryBuilder.addPropertyValue("connectionOptions",mqttConnectOptions);
            beanFactory.registerBeanDefinition("mqttClientFactory" + i,mqttFactoryBuilder.getRawBeanDefinition());
            int j = 0;
            for (MqttProperties.Topic topic : driver.getTopics()) {
                if (initList.contains(topic.getTopicName())){
                    continue;
                }
                //订阅
                if (ModelEnum.subscribe.toString().equalsIgnoreCase(topic.getModel())) {
                    BeanDefinitionBuilder directChannelBuilder = BeanDefinitionBuilder.genericBeanDefinition(DirectChannel.class);
                    directChannelBuilder.addConstructorArgValue(new RoundRobinLoadBalancingStrategy());
                    beanFactory.registerBeanDefinition(topic.getTopicName(),directChannelBuilder.getRawBeanDefinition());
                    //adapter
                    BeanDefinitionBuilder adapterBuilder = BeanDefinitionBuilder.genericBeanDefinition(MqttPahoMessageDrivenChannelAdapter.class);
                    adapterBuilder.addConstructorArgValue(driver.getUrl());
                    adapterBuilder.addConstructorArgValue(driver.getClientId() + "_" + ModelEnum.subscribe.toString());
                    adapterBuilder.addConstructorArgValue(applicationContext.getBean("mqttClientFactory" + i));
                    adapterBuilder.addConstructorArgValue(topic.getTopicName());
                    adapterBuilder.addPropertyValue("qos",topic.getQos());
                    adapterBuilder.addPropertyValue("outputChannel",applicationContext.getBean(topic.getTopicName()));
                    adapterBuilder.addPropertyValue("converter",new DefaultPahoMessageConverter());
                    beanFactory.registerBeanDefinition("inbound" + j,adapterBuilder.getRawBeanDefinition());
                }
                //发布
                if (ModelEnum.publish.toString().equalsIgnoreCase(topic.getModel())) {
                    BeanDefinitionBuilder publishHandlerBuilder = BeanDefinitionBuilder.genericBeanDefinition(MqttPahoMessageHandler.class);
                    publishHandlerBuilder.addConstructorArgValue(driver.getUrl());
                    publishHandlerBuilder.addConstructorArgValue(driver.getClientId() + "_" + ModelEnum.publish.toString());
                    publishHandlerBuilder.addConstructorArgValue(applicationContext.getBean("mqttClientFactory" + i));
                    publishHandlerBuilder.addPropertyValue("async",topic.isAsync());
                    publishHandlerBuilder.addPropertyValue("defaultQos",topic.getQos());
                    publishHandlerBuilder.addPropertyValue("defaultTopic",topic.getTopicName());
                    publishHandlerBuilder.addPropertyValue("defaultRetained",topic.isRetained());
                    beanFactory.registerBeanDefinition(topic.getTopicName() + "_" + ModelEnum.publish.toString(),publishHandlerBuilder.getRawBeanDefinition());
                }
                initList.add(topic.getTopicName() + "_" + topic.getModel());
                j++;
                log.info("------>topic:[{}]-model[{}] init success.",topic.getTopicName(),topic.getModel());
            }
            i++;
            initList.add(driver.getUrl());
        }
    }
}
