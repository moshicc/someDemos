package com.zcc.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author zcc
 * @ClassName MessageListenerConfig
 * @description 消息消费，确认
 * @date 2021/10/22 16:29
 * @Version 1.0
 */
@Configuration
public class MessageListenerConfig {

    @Autowired
    private CachingConnectionFactory connectionFactory;
    @Autowired
    private MyAckReceiver myAckReceiver; //消息接收处理类

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setConcurrentConsumers(1);
        container.setMaxConcurrentConsumers(1);

        // RabbitMQ默认是自动确认，这里改为手动确认消息
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        //设置一个队列
        container.setQueueNames("TestDirectQueue");

        //如果同时设置多个如下： 前提是队列都是必须已经创建存在的
//        container.setQueueNames("TestDirectQueue","TestDirectQueue2","TestDirectQueue3");

        //另一种设置队列的方法，如果使用这种情况，那么要设置多个，就使用addQueue
//        container.addQueues(new Queue("TestDirectQueue", true));
//        container.addQueues(new Queue("TestDirectQueue", true));
//        container.addQueues(new Queue("TestDirectQueue", true));

        container.setMessageListener(myAckReceiver);

        return container;
    }


}
