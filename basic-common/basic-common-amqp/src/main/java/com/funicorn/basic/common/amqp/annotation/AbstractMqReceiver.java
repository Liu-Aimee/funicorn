package com.funicorn.basic.common.amqp.annotation;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;

import java.io.IOException;

/**
 * 同步处理队列
 * @author Aimee
 * @since 2021/9/30 16:47
 */
public abstract class AbstractMqReceiver {

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
        try {
            doBusiness(channel,message,msg);
            //手动ack
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                //重新放入队列
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
