package com.zcc.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zcc
 * @ClassName DirectRabbitConfig
 * https://blog.csdn.net/qq_35387940/article/details/100514134
 * 消费者单纯的使用，其实可以不用添加这个配置，直接建后面的监听就好，
 * 使用注解来让监听器监听对应的队列即可。
 * 配置上了的话，其实消费者也是生成者的身份，也能推送该消息。
 * @description direct exchange(直连型交换机)
 * @date 2021/10/22 14:48
 * @Version 1.0
 */
@Configuration
public class DirectRabbitConfig {

    public static final String queueName = "TestDirectQueue";
    public static final String exchangeName = "TestDirectExchange";
    public static final String routingKey = "TestDirectRouting";


    //队列 起名：TestDirectQueue
    @Bean
    public Queue TestDirectQueue() {
        return new Queue(queueName, true);
    }

    //Direct 交换机 起名：TestDirectExchange
    @Bean
    public DirectExchange TestDirectExchange() {
        //  return new DirectExchange("TestDirectExchange",true,true);
        return new DirectExchange(exchangeName);
    }

    //绑定  将队列和交换机绑定, 并设置用于匹配键：TestDirectRouting
    @Bean
    public Binding bindingDirect() {
        return BindingBuilder.bind(TestDirectQueue()).to(TestDirectExchange()).with("TestDirectRouting");
    }

}
