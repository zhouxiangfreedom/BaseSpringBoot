package com.zhou.basespringboot.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author zx
 * @date 2019/11/26 10:57
 */
@Component
@RabbitListener(queues = {"sms"}, containerFactory = "smsListenerContainerFactory")
public class RabbitMqSmsListener {
    @RabbitHandler(isDefault = true)
    public void handle(@Payload byte[] bytes, @Headers Map<String, Object> headers) throws UnsupportedEncodingException {
        System.out.println("[x] 1.Receive sms message:[" + new String(bytes, "UTF-8") + "].");
    }

    @RabbitHandler
    public void handle2(Message message) {
        String correlationId = message.getMessageProperties().getCorrelationId();
        System.out.println("[x] 2.Receive sms message:[" + new String(message.getBody()) + "].");
    }
}
