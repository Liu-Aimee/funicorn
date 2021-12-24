package com.funicorn.basic.common.mqtt.util;

import com.funicorn.basic.common.base.util.AppContextUtil;
import com.funicorn.basic.common.mqtt.config.ModelEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

/**
 * MQTT 工具类
 * @author Aimee
 * @since 2021/12/24 10:49
 */
@Slf4j
public class MqttUtil {

    /**
     * MQTT发送消息
     * @param message 消息内容
     * @param topic 主题
     * */
    public static void sendMessage(String message,String topic) {
        sendMessage(message,topic,0);
    }

    /**
     * MQTT发送消息
     * @param message 消息内容
     * @param topic 主题
     * @param qos 等级
     * */
    public static void sendMessage(String message,String topic,Integer qos) {
        MqttPahoMessageHandler handler = (MqttPahoMessageHandler) AppContextUtil.getBean(topic + "_" + ModelEnum.publish.toString());
        Message<String> mqttMessage = MessageBuilder.withPayload(message).setHeader(MqttHeaders.TOPIC, topic)
                .setHeader(MqttHeaders.QOS, qos==null ? 0 : qos).build();
        handler.handleMessage(mqttMessage);
    }

}
