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
            //创建连接connect
            connect = factory.newConnection("生产者");
            //通过连接获取通道Channel
            channel = connect.createChannel();
            String queueName = "queue5";
            //通过创建交换机，声明队列，绑定关系，路由key，发送消息和接收消息
            channel.queueDeclare(queueName,false,false,false,null);
            channel.basicPublish("",queueName,null, "hello RabbitMQ".getBytes("UTF-8"));

            System.out.println("----发送消息5成功-----");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            //关闭连接
            closeConnect(channel, connect);
        }
    }
    //将关闭连接的代码提出来，改成一个方法
    static void closeConnect(Channel channel, Connection connect) {
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
