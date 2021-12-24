package com.funicorn.basic.common.rocketmq.processor;

import com.funicorn.basic.common.rocketmq.model.ConsumeResult;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author Aimme
 * @since 2020/10/28 18:13
 */
public interface MsgProcessor {

    /**
     * 消息处理函数
     * @param topic 主题
     * @param tag tag
     * @param msg 消息
     * @return ConsumeResult
     * */
    ConsumeResult handle(String topic, String tag, List<MessageExt> msg);
}
