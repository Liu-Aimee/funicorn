package com.funicorn.cloud.system.center.mqtt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

/**
 * mqtt订阅处理器示例
 * @author Aimee
 * @since 2021/12/24 10:10
 */
@Component
@Slf4j
public class DemoReceiveHandler {

    @SuppressWarnings("all")
    @Bean
    @ServiceActivator(inputChannel = "demo/test/pop")
    public MessageHandler handler(){
        return message ->{
            log.info(message.getPayload().toString());
        };
    }
}
