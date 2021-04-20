package com.zcc.rabbitmq.simple;

import com.rabbitmq.client.*;
import com.rabbitmq.client.impl.AMQImpl;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zcc
 * @ClassName rabbitMGconsumer
 * @description 消费者
 * @date 2021/4/19 19:39
 * @Version 1.0
 */

public class rabbitMGconsumer {

    private static String host = "47.93.228.227";
    private static String userName = "guest";
    private static String passWord = "guest";
    //用docker部署的rabbitMQ 内部的5672映射到linux服务器上的8084
    // （为啥不映射到linux上的5672端口？狗日的，阿里云安全组配置的5672总是不生效！）
    private static int port = 8085;

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(userName);
        factory.setPassword(passWord);

        Channel channel = null;
        Connection connect = null;
        try {
            connect = factory.newConnection("消费者");
            channel = connect.createChannel();

            String queueName = "queue5";
            channel.queueDeclare(queueName,false,false,false,null);
            QueueingConsumer consumer = new QueueingConsumer(channel);
            channel.basicConsume(queueName, true, consumer);
            while (true) {
                QueueingConsumer.Delivery deliver = consumer.nextDelivery();
                System.out.println("reciver messager:"+new String(deliver.getBody()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            //关闭连接
            rabbitMQproducer.closeConnect(channel, connect);
        }
    }
}
