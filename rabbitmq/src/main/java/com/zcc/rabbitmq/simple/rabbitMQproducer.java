package com.zcc.rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zcc
 * @ClassName rabbitMQproducer
 * @description 生产者
 * @date 2021/4/19 19:39
 * @Version 1.0
 */

public class rabbitMQproducer {
    private static String host = "47.93.228.227";
    private static String userName = "guest";
    private static String passWord = "guest";
    private static int port = 5672;

    public static void main(String[] args) {

            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(host);
            factory.setPort(port);
            factory.setUsername(userName);
            factory.setPassword(passWord);

            Channel channel = null;
            Connection connect = null;
        try {
            connect = factory.newConnection("生产者");
            channel = connect.createChannel();
            String queueName = "queue1";
            channel.queueDeclare(queueName,false,false,false,null);

            System.out.println("----发送消息成功-----");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            if (channel != null && channel.isOpen()) {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
            if (connect != null && connect.isOpen()) {
                try {
                    connect.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
