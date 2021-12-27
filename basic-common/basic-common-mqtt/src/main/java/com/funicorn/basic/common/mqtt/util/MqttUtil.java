package com.funicorn.basic.common.mqtt.util;

import com.funicorn.basic.common.base.util.AppContextUtil;
import com.funicorn.basic.common.mqtt.model.MqttMsgHeader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageHeaders;

import java.util.Objects;

/**
 * MQTT 工具类
 * @author Aimee
 * @since 2021/12/24 10:49
 */
@Slf4j
public class MqttUtil {

    /**
     * 默认第一通道名称为默认通道
     * */
    public static String DEFAULT_CHANNEL_NAME = null;

    /**
     * MQTT发送消息
     * @param message 消息内容
     * */
    public static void sendMessage(String message) {
        if (StringUtils.isBlank(DEFAULT_CHANNEL_NAME)) {
            throw new IllegalArgumentException("MqttUtil.DEFAULT_CHANNEL_NAME is null or Empty!");
        }
        sendMessage(DEFAULT_CHANNEL_NAME,message,null,null);
    }

    /**
     * MQTT发送消息
     * @param channelName 通道名称
     * @param message 消息内容
     * */
    public static void sendMessage(String channelName,String message) {
        sendMessage(channelName,message,null,null);
    }

    /**
     * MQTT发送消息
     * @param channelName 通道名称
     * @param message 消息内容
     * @param topic 主题
     * */
    public static void sendMessage(String channelName,String message,String topic) {
        sendMessage(channelName,message,topic,null);
    }

    /**
     * MQTT发送消息
     * @param channelName 通道名称
     * @param message 消息内容
     * @param topic 主题
     * @param qos 等级
     * */
    public static void sendMessage(String channelName,String message,String topic,Integer qos) {
        MqttPahoMessageHandler handler = (MqttPahoMessageHandler) AppContextUtil.getBean(channelName);
        MessageBuilder<String> messageBuilder = MessageBuilder.withPayload(message);
        if (StringUtils.isNotBlank(topic)) {
            messageBuilder.setHeader(MqttHeaders.TOPIC, topic);
        }
        if (qos!=null && qos >= 0) {
            messageBuilder.setHeader(MqttHeaders.QOS, qos);
        }
        handler.handleMessage(messageBuilder.build());
    }

    /**
     * 解析mqtt message请求头
     * @param messageHeaders messageHeaders
     * @return MqttMsgHeader
     * */
    public static MqttMsgHeader getHeader(MessageHeaders messageHeaders){
        MqttMsgHeader mqttMsgHeader = new MqttMsgHeader();
        mqttMsgHeader.setId(Objects.requireNonNull(messageHeaders.getId()).toString());
        mqttMsgHeader.setTopic((String) messageHeaders.get("mqtt_receivedTopic"));
        mqttMsgHeader.setQos((Integer) messageHeaders.get("mqtt_receivedQos"));
        mqttMsgHeader.setTimestamp(messageHeaders.getTimestamp());
        mqttMsgHeader.setDuplicate(messageHeaders.get("mqtt_duplicate",Boolean.class));
        mqttMsgHeader.setRetained(messageHeaders.get("mqtt_receivedRetained",Boolean.class));
        return mqttMsgHeader;
    }
}
