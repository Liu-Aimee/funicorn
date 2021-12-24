package com.funicorn.basic.common.amqp.annotation;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author Aimee
 * @since 2021/9/30 16:47
 */
@Slf4j
public abstract class AbstractAsyncMqReceiver {

    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * RabbitMQ 消费函数
     * @param channel 通道信息
     * @param message 附加参数
     * @param msg 消息内容
     * @throws Exception 异常
     * */
    public abstract void doBusiness(Channel channel, Message message,String msg) throws Exception;

    @RabbitHandler
    protected void execute(Channel channel, Message message,String msg){
        threadPoolTaskExecutor.execute(() -> {
            try {
                doBusiness(channel,message,msg);
                //手动ack
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (Exception e) {
                log.error("RabbitMQ消费失败",e);
                try {
                    //重新放入队列
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }
}
