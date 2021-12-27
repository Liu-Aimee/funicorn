package com.funicorn.basic.common.mqtt.model;

import lombok.Data;
import lombok.ToString;

/**
 * @author Aimee
 * @since 2021/12/25 10:54
 */
@Data
@ToString
public class MqttMsgHeader {

    /**
     * 唯一id
     * */
    private String id;

    /**
     * 主题
     * */
    private String topic;

    /**
     * 消息等级
     * */
    private Integer qos;

    /**
     * 是否保留最后一条消息
     * */
    private Boolean retained;

    /**
     * 是否重复发送
     * */
    private Boolean duplicate;

    /**
     * 消息发送时间长
     * */
    private Long timestamp;
}
