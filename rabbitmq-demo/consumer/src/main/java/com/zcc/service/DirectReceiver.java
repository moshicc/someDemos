package com.zcc.service;

import com.zcc.config.DirectRabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zcc
 * @ClassName DirectReceiver
 * @description 创建消息接收监听类
 * @date 2021/10/22 15:32
 * @Version 1.0
 */

@Component
@RabbitListener(queues = "TestDirectQueue") //监听的队列名称 TestDirectQueue
public class DirectReceiver {

    @RabbitHandler
    public void process(Map testMessage) {
        System.out.println("DirectReceiver消费者收到消息  : " + testMessage.toString());
    }
}
