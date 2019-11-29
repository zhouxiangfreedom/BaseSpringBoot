package com.zhou.basespringboot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhou.basespringboot.common.constants.RabbitMqConstants;
import com.zhou.basespringboot.dto.rabbitmq.LogDTO;
import com.zhou.basespringboot.dto.rabbitmq.MailDTO;
import com.zhou.basespringboot.dto.rabbitmq.OrderDTO;
import com.zhou.basespringboot.dto.rabbitmq.SmsDTO;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author zx
 * @date 2019/4/2 16:59
 */
@Service
public class RabbitMqService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    @Qualifier("confirmRabbitTemplate")
    private RabbitTemplate confirmRabbitTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 发送log消息
     */
    public void sendLog() throws JsonProcessingException {
        String prefix = "LOG,Hello World-";
        for (int i = 0; i < 100; i++) {
            LogDTO dto = new LogDTO(prefix + i);
            Message msg = MessageBuilder.withBody(objectMapper.writeValueAsBytes(dto)).build();
            msg.getMessageProperties().setContentType(MessageProperties.CONTENT_TYPE_JSON);
            rabbitTemplate.convertAndSend(RabbitMqConstants.EXCHANGE_BASE_LOG, RabbitMqConstants.QUEUE_LOG, msg);
        }
    }

    /**
     * 发送mail消息
     */
    public void sendMail() throws JsonProcessingException {
        String prefix = "MAIL,Hello World-";
        for (int i = 0; i < 10; i++) {
            MailDTO mailDTO = new MailDTO(prefix + i);
            Message msg = MessageBuilder.withBody(objectMapper.writeValueAsBytes(mailDTO)).setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
            msg.getMessageProperties().setContentType(MessageProperties.CONTENT_TYPE_JSON);
            msg.getMessageProperties().setExpiration("10000");
            CorrelationData data = new CorrelationData(UUID.randomUUID().toString());
            confirmRabbitTemplate.convertAndSend(RabbitMqConstants.EXCHANGE_BASE_MAIL, RabbitMqConstants.QUEUE_MAIL, msg, data);
        }
    }

    /**
     * 发送order消息
     *
     * @throws JsonProcessingException
     */
    public void sendOrder() throws JsonProcessingException {
        String prefix = "Delayed Order,Hello World-";
        for (int i = 0; i < 10; i++) {
            OrderDTO mailDTO = new OrderDTO(prefix + i);
            Message msg = MessageBuilder
                    .withBody(objectMapper.writeValueAsBytes(mailDTO))
                    // 持久化
                    .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                    .build();
            // json格式
            msg.getMessageProperties().setContentType(MessageProperties.CONTENT_TYPE_JSON);
            // TTL 10000ms
            msg.getMessageProperties().setExpiration("10000");
            CorrelationData data = new CorrelationData(UUID.randomUUID().toString());
            confirmRabbitTemplate.convertAndSend(RabbitMqConstants.EXCHANGE_BASE_ORDER, RabbitMqConstants.QUEUE_ORDER, msg, data);
        }
    }

    /**
     * 发送sms消息
     */
    public void sendSms() throws JsonProcessingException {
        String prefix = "Sms,Hello World-";
        for (int i = 0; i < 10; i++) {
            SmsDTO smsDTO = new SmsDTO(prefix + i);
            Message msg = MessageBuilder.withBody(objectMapper.writeValueAsBytes(smsDTO)).setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
            msg.getMessageProperties().setContentType(MessageProperties.CONTENT_TYPE_JSON);
            msg.getMessageProperties().setExpiration("10000");
            CorrelationData data = new CorrelationData(UUID.randomUUID().toString());
            confirmRabbitTemplate.convertAndSend(RabbitMqConstants.EXCHANGE_BASE_SMS, RabbitMqConstants.QUEUE_SMS, msg, data);
        }
    }
}
