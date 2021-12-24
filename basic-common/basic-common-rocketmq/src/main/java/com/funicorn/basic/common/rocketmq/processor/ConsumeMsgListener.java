package com.funicorn.basic.common.rocketmq.processor;

import com.alibaba.fastjson.JSON;
import com.funicorn.basic.common.rocketmq.annotation.MQConsumeService;
import com.funicorn.basic.common.rocketmq.model.ConsumeResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Aimme
 * @since 2020/10/28 18:18
 */
@Component
@Slf4j
public class ConsumeMsgListener implements MessageListenerConcurrently {

    @Resource
    private Map<String, MsgProcessor> mqMsgProcessorServiceMap;

    /**
     *  默认msg里只有一条消息，可以通过设置consumeMessageBatchMaxSize参数来批量接收消息
     *  不要抛异常，如果没有return CONSUME_SUCCESS ，consumer会重新消费该消息，直到return CONSUME_SUCCESS
     *
     * @param msg 消息
     * @param context 上下文
     * @return ConsumeConcurrentlyStatus
     */
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msg, ConsumeConcurrentlyContext context) {
        if(CollectionUtils.isEmpty(msg)){
            log.info("接受到的消息为空，不处理，直接返回成功");
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        ConsumeConcurrentlyStatus concurrentlyStatus = ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        try{
            //根据Topic分组
            Map<String, List<MessageExt>> topicGroups = msg.stream().collect(Collectors.groupingBy(MessageExt::getTopic));
            for (Map.Entry<String, List<MessageExt>> topicEntry : topicGroups.entrySet()) {
                String topic = topicEntry.getKey();
                //根据tags分组
                Map<String, List<MessageExt>> tagGroups = topicEntry.getValue().stream().collect(Collectors.groupingBy(MessageExt::getTags));
                for (Map.Entry<String, List<MessageExt>> tagEntry : tagGroups.entrySet()) {
                    String tag = tagEntry.getKey();
                    //消费某个主题下，tag的消息
                    this.consumeMsgForTag(topic,tag,tagEntry.getValue());
                }
            }
        }catch(Exception e){
            log.error("处理消息失败",e);
            concurrentlyStatus = ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        // 如果没有return success ，consumer会重新消费该消息，直到return success
        return concurrentlyStatus;
    }
    /**
     * 根据topic 和 tags路由，查找消费消息服务
     * @param topic 主题
     * @param tag  标签
     * @param value 消息
     */
    private void consumeMsgForTag(String topic, String tag, List<MessageExt> value) {
        //根据topic 和  tag查询具体的消费服务
        MsgProcessor imqMsgProcessor = selectConsumeService(topic, tag);

        if(imqMsgProcessor==null){
            log.error(String.format("根据Topic：%s和Tag:%s 没有找到对应的处理消息的服务",topic,tag));
            throw new RuntimeException("没有找到对应的处理消息的服务");
        }
        log.info(String.format("根据Topic：%s和Tag:%s 路由到的服务为:%s，开始调用处理消息",topic,tag,imqMsgProcessor.getClass().getName()));
        //调用该类的方法,处理消息
        ConsumeResult result = imqMsgProcessor.handle(topic,tag,value);

        if(result==null){
            throw new RuntimeException("result is null");
        }
        if(!result.isSuccess()){
            log.error("消息处理失败,{}",JSON.toJSONString(result));
            throw new RuntimeException(JSON.toJSONString(result));
        }
    }
    /**
     * 根据topic和tag查询对应的具体的消费服务
     * @param topic 主题
     * @param tag  标签
     */
    private MsgProcessor selectConsumeService(String topic, String tag) {
        MsgProcessor imqMsgProcessor = null;
        for (Map.Entry<String, MsgProcessor> entry : mqMsgProcessorServiceMap.entrySet()) {
            //获取service实现类上注解的topic和tags
            MQConsumeService consumeService = entry.getValue().getClass().getAnnotation(MQConsumeService.class);
            if(consumeService == null){
                log.error("消费者服务："+entry.getValue().getClass().getName()+"上没有添加MQConsumeService注解");
                continue;
            }
            String annotationTopic = consumeService.topic();
            if(!annotationTopic.equals(topic)){
                continue;
            }

            List<String> tags = Arrays.asList(consumeService.tags());

            //"*"号表示订阅该主题下所有的tag
            if("*".equals(tag) || tags.contains(tag)){
                //获取该实例
                imqMsgProcessor = entry.getValue();
                break;
            }
        }
        return imqMsgProcessor;
    }

}
