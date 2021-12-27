package com.funicorn.basic.common.mqtt.config;

import com.funicorn.basic.common.mqtt.property.MqttProperties;
import com.funicorn.basic.common.mqtt.util.MqttUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import java.util.LinkedList;
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
                throw new IllegalArgumentException("存在重复的mqtt.drivers.url,请检查!");
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
            //订阅列表
            if (driver.getSubscribe()!=null && driver.getSubscribe().getTopics()!=null && !driver.getSubscribe().getTopics().isEmpty()) {
                if (StringUtils.isBlank(driver.getSubscribe().getChannelName())){
                    throw new IllegalArgumentException("mqtt.drivers.publish.channel-name 配置不合法!");
                }
                if (initList.contains(driver.getSubscribe().getChannelName())) {
                    throw new IllegalArgumentException("存在重复的channel-name,请检查!");
                }
                BeanDefinitionBuilder directChannelBuilder = BeanDefinitionBuilder.genericBeanDefinition(DirectChannel.class);
                directChannelBuilder.addConstructorArgValue(new RoundRobinLoadBalancingStrategy());
                beanFactory.registerBeanDefinition(driver.getSubscribe().getChannelName(),directChannelBuilder.getRawBeanDefinition());

                List<String> topics = new LinkedList<>();
                List<Integer> qosList = new LinkedList<>();
                for (MqttProperties.Topic topic : driver.getSubscribe().getTopics()) {
                    if (StringUtils.isBlank(topic.getTopic())) {
                        throw new IllegalArgumentException("mqtt.drivers.subscribe 配置不合法!");
                    }
                    topics.add(topic.getTopic());
                    qosList.add(topic.getQos()==null ? 0 : topic.getQos());
                }
                BeanDefinitionBuilder adapterBuilder = BeanDefinitionBuilder.genericBeanDefinition(MqttPahoMessageDrivenChannelAdapter.class);
                adapterBuilder.addConstructorArgValue(driver.getUrl());
                adapterBuilder.addConstructorArgValue(driver.getClientIdPrefix() + "_" + ModelEnum.subscribe.toString());
                adapterBuilder.addConstructorArgValue(applicationContext.getBean("mqttClientFactory" + i));
                adapterBuilder.addConstructorArgValue(topics.toArray());
                adapterBuilder.addPropertyValue("qos",qosList.toArray());
                adapterBuilder.addPropertyValue("outputChannel",applicationContext.getBean(driver.getSubscribe().getChannelName()));
                adapterBuilder.addPropertyValue("converter",new DefaultPahoMessageConverter());
                beanFactory.registerBeanDefinition("inbound_" + driver.getSubscribe().getChannelName(),adapterBuilder.getRawBeanDefinition());
                initList.add(driver.getPublish().getChannelName());
                log.info("------>model[subscribe] and channelName[{}] init success.",driver.getSubscribe().getChannelName());
            }
            //发布列表
            if (driver.getPublish()!=null) {
                if (StringUtils.isBlank(driver.getPublish().getChannelName())){
                    throw new IllegalArgumentException("mqtt.drivers.publish.channel-name 配置不合法!");
                }
                if (initList.contains(driver.getSubscribe().getChannelName())) {
                    throw new IllegalArgumentException("存在重复的channel-name,请检查!");
                }
                if (StringUtils.isBlank(driver.getPublish().getDefaultTopic())){
                    throw new IllegalArgumentException("mqtt.drivers.publish.default-topic 配置不合法!");
                }
                BeanDefinitionBuilder publishHandlerBuilder = BeanDefinitionBuilder.genericBeanDefinition(MqttPahoMessageHandler.class);
                publishHandlerBuilder.addConstructorArgValue(driver.getUrl());
                publishHandlerBuilder.addConstructorArgValue(driver.getClientIdPrefix() + "_" + ModelEnum.publish.toString());
                publishHandlerBuilder.addConstructorArgValue(applicationContext.getBean("mqttClientFactory" + i));
                publishHandlerBuilder.addPropertyValue("async", driver.getPublish().getAsync() == null || driver.getPublish().getAsync());
                publishHandlerBuilder.addPropertyValue("defaultQos",driver.getPublish().getDefaultQos());
                publishHandlerBuilder.addPropertyValue("defaultTopic",driver.getPublish().getDefaultTopic());
                publishHandlerBuilder.addPropertyValue("defaultRetained",driver.getPublish().getDefaultRetained() == null || driver.getPublish().getDefaultRetained());
                beanFactory.registerBeanDefinition(driver.getPublish().getChannelName(),publishHandlerBuilder.getRawBeanDefinition());
                initList.add(driver.getPublish().getChannelName());
                log.info("------>model[publish] and channelName[{}] init success.",driver.getPublish().getChannelName());
                if (StringUtils.isBlank(MqttUtil.DEFAULT_CHANNEL_NAME)) {
                    MqttUtil.DEFAULT_CHANNEL_NAME = driver.getPublish().getChannelName();
                    log.info("------>set the default_channel_name is [{}].",driver.getPublish().getChannelName());
                }
            }
            i++;
            initList.add(driver.getUrl());
        }
    }
}
