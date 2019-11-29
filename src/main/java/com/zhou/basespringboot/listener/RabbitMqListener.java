package com.zhou.basespringboot.listener;

import com.rabbitmq.client.Channel;
import com.zhou.basespringboot.common.constants.RabbitMqConstants;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author zx
 * @date 2019/11/22 10:35
 */
@Log4j2
@Component
public class RabbitMqListener {

    /**
     * log消费
     *
     * @param message
     */
    @RabbitListener(queues = {RabbitMqConstants.QUEUE_LOG}, containerFactory = "multiListenerContainerFactory")
    public void listenLog(Message message) {
        System.out.println("[x] " + Thread.currentThread() + " Receive log message [" + new String(message.getBody()) + "].");
    }

    /**
     * mail消费
     *
     * @param message
     * @param channel
     */
    @RabbitListener(queues = {RabbitMqConstants.QUEUE_MAIL}, containerFactory = "singleListenerContainerFactory")
    public void listenMail(Message message, Channel channel) {
        while (true) {
            try {
                System.out.println("[x] Receive mail message [" + new String(message.getBody()) + "].");
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                break;
            } catch (Exception e) {
                log.error("发送邮件出现异常，请检查，message:{}", new String(message.getBody()));
            }
        }
    }

    /**
     * 死信消费
     *
     * @param message
     */
    @RabbitListener(bindings = {@QueueBinding(
            value = @Queue(value = RabbitMqConstants.QUEUE_ORDER_DEAD, durable = "true"),
            exchange = @Exchange(value = RabbitMqConstants.EXCHANGE_BASE_ORDER_DEAD),
            key = RabbitMqConstants.QUEUE_ORDER_DEAD)},
            containerFactory = "singleDlListenerContainerFactory")
    public void listenDelayedOrder(Message message) {
        System.out.println("[x] Receive delayed order message [" + new String(message.getBody()) + "].");
    }
}
