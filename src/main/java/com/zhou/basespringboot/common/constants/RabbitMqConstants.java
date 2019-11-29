package com.zhou.basespringboot.common.constants;

/**
 * @author zx
 * @date 2019/11/25 15:48
 */
public interface RabbitMqConstants {
    String EXCHANGE_BASE_LOG = "base.log";
    String EXCHANGE_BASE_MAIL = "base.mail";
    String EXCHANGE_BASE_ORDER = "base.order";
    String EXCHANGE_BASE_ORDER_DEAD = "base.order.dl";
    String EXCHANGE_BASE_SMS = "base.sms";
    String QUEUE_LOG = "log";
    String QUEUE_MAIL = "mail";
    String QUEUE_ORDER = "order";
    String QUEUE_ORDER_DEAD = "order.dl";
    String QUEUE_SMS = "sms";
}
