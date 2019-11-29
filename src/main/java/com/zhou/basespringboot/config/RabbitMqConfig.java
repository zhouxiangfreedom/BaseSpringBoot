package com.zhou.basespringboot.config;

import com.zhou.basespringboot.common.constants.RabbitMqConstants;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zx
 * @date 2019/4/2 17:01
 */
@Log4j2
@Configuration
public class RabbitMqConfig {
    @Autowired
    @Qualifier("defaultConnectionFactory")
    private CachingConnectionFactory connectionFactory;
    @Autowired
    private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;

    @Bean("defaultConnectionFactory")
    @Primary
    public CachingConnectionFactory connectionFactory() {
        //地址及端口号
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost", 5673);
        //虚拟主机
        connectionFactory.setVirtualHost("/");
        //用户名，密码
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    /**
     * 一个消费者
     *
     * @return
     */
    @Bean("singleListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory listenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factoryConfigurer.configure(factory, connectionFactory);
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setMaxConcurrentConsumers(1);
        factory.setConcurrentConsumers(1);
        factory.setPrefetchCount(1);
        factory.setTxSize(1);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }


    /**
     * 单个消费者 dlx
     *
     * @return
     */
    @Bean("singleDlListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory singleDlListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factoryConfigurer.configure(factory, connectionFactory);
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setMaxConcurrentConsumers(1);
        factory.setConcurrentConsumers(1);
        factory.setPrefetchCount(1);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return factory;
    }

    /**
     * 多个消费者
     *
     * @return
     */
    @Bean("smsListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory smsListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factoryConfigurer.configure(factory, connectionFactory);
        factory.setConnectionFactory(connectionFactory);
//        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setMaxConcurrentConsumers(1);
        factory.setConcurrentConsumers(1);
        factory.setPrefetchCount(1);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return factory;
    }

    /**
     * 多个消费者
     *
     * @return
     */
    @Bean("multiListenerContainerFactory")
    @Primary
    public SimpleRabbitListenerContainerFactory multiListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factoryConfigurer.configure(factory, connectionFactory);
        factory.setConnectionFactory(connectionFactory);
        //指定消息转换器
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        //最大消费者数量
        factory.setMaxConcurrentConsumers(10);
        //并发消费者数量
        factory.setConcurrentConsumers(5);
        //信道内所有消费者未提交的消息最大数量
        factory.setPrefetchCount(10);
        //信道事务内最大消息数
        factory.setTxSize(10);
        //消费ack模式，node：自动提交，auto：根据消费端是否有异常提交，manual：手动提交确认
        factory.setAcknowledgeMode(AcknowledgeMode.NONE);
        return factory;
    }


    /**
     * Confirm模式的RabbitTemplate
     *
     * @return
     */
    @Bean("confirmRabbitTemplate")
    public RabbitTemplate rabbitTemplate() {
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                log.info("消息发送成功，correlationData:{}，ack:{}，cause:{}", correlationData == null ? "" : correlationData.getId(), ack, cause);
            }
        });
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                log.info("消息发送失败，exchange:{}，route:{}，replyCode:{}，replyText:{}，msg:{}", exchange, routingKey, replyCode, replyText, new String(message.getBody()));
            }
        });
        return rabbitTemplate;
    }


    /**
     * 正常模式的RabbitTemplate
     *
     * @return
     */
    @Primary
    @Bean("commonRabbitTemplate")
    public RabbitTemplate commonRabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    //------------------以下声明主要是在RabbitMQ broker 上创建 交换器、队列------------------

    /**
     * log队列
     *
     * @return
     */
    @Bean("logQueue")
    public Queue logQueue() {
        return QueueBuilder.durable(RabbitMqConstants.QUEUE_LOG).build();
    }

    /**
     * log交换器
     *
     * @return
     */
    @Bean("logExchange")
    public Exchange logExchange() {
        return ExchangeBuilder.directExchange(RabbitMqConstants.EXCHANGE_BASE_LOG).durable(true).build();
    }

    /**
     * log绑定
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean("logBinding")
    public Binding logBinding(@Qualifier("logQueue") Queue queue, @Qualifier("logExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(queue.getName()).noargs();
    }

    /**
     * 死信队列 order.dl
     *
     * @return
     */
    @Bean("deadLetterOrderQueue")
    public Queue deadLetterMailQueue() {
        return QueueBuilder.durable(RabbitMqConstants.QUEUE_ORDER_DEAD).build();
    }

    /**
     * 死信交换器 base.order.dl
     *
     * @return
     */
    @Bean("deadLetterOrderExchange")
    public Exchange deadLetterMailExchange() {
        return ExchangeBuilder.directExchange(RabbitMqConstants.EXCHANGE_BASE_ORDER_DEAD).durable(true).build();
    }

    /**
     * order 队列
     *
     * @return
     */
    @Bean("orderQueue")
    public Queue orderQueue() {
        Map<String, Object> args = new HashMap<>(16);
        args.put("x-dead-letter-exchange", RabbitMqConstants.EXCHANGE_BASE_ORDER_DEAD);
        args.put("x-dead-letter-routing-key", RabbitMqConstants.QUEUE_ORDER_DEAD);
        return QueueBuilder.durable(RabbitMqConstants.QUEUE_ORDER).withArguments(args).build();
    }

    /**
     * order交换器
     *
     * @return
     */
    @Bean("orderExchange")
    public Exchange orderExchange() {
        return ExchangeBuilder.directExchange(RabbitMqConstants.EXCHANGE_BASE_ORDER).durable(true).build();
    }

    /**
     * order绑定
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean("orderBinding")
    public Binding orderBinding(@Qualifier("orderQueue") Queue queue, @Qualifier("orderExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(RabbitMqConstants.QUEUE_ORDER).noargs();
    }

    /**
     * mail队列
     *
     * @return
     */
    @Bean("mailQueue")
    public Queue mailQueue() {
        return QueueBuilder.durable(RabbitMqConstants.QUEUE_MAIL).build();
    }

    /**
     * mail交换器
     *
     * @return
     */
    @Bean("mailExchange")
    public Exchange mailExchange() {
        return ExchangeBuilder.directExchange(RabbitMqConstants.EXCHANGE_BASE_MAIL).durable(true).build();
    }

    /**
     * mail绑定
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean("mailBinding")
    public Binding mailBinding(@Qualifier("mailQueue") Queue queue, @Qualifier("mailExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(queue.getName()).noargs();
    }


    /**
     * sms队列
     *
     * @return
     */
    @Bean("smsQueue")
    public Queue smsQueue() {
        return QueueBuilder.durable(RabbitMqConstants.QUEUE_SMS).build();
    }

    /**
     * sms交换器
     *
     * @return
     */
    @Bean("smsExchange")
    public Exchange smsExchange() {
        return ExchangeBuilder.directExchange(RabbitMqConstants.EXCHANGE_BASE_SMS).durable(true).build();
    }

    /**
     * mail绑定
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean("smsBinding")
    public Binding smsBinding(@Qualifier("smsQueue") Queue queue, @Qualifier("smsExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(queue.getName()).noargs();
    }
}
