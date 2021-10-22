package com.zcc.config;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zcc
 * @ClassName MyAckReceiver
 * @description 手动确认消息监听类，MyAckReceiver.java（手动确认模式需要实现 ChannelAwareMessageListener）
 * 这里的获取消息转换，只作参考，如果报数组越界可以自己根据格式去调整。
 * @date 2021/10/22 16:32
 * @Version 1.0
 */
@Component
public class MyAckReceiver implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        try {
            //因为传递消息的时候使用map传递，所以将Map 从Message内取出来需要做些处理
            String msg = message.toString();
            //可以点进Message里面看源码,单引号直接的数据就是我们的map消息数据
            String[] msgArray = msg.split("'");
            Map<String, String> msgMap = mapStringToMap(msgArray[1].trim(), 3);
            String messageId = msgMap.get("messageId");
            String messageData = msgMap.get("messageData");
            String createTime = msgMap.get("createTime");

            System.out.println("  MyAckReceiver  messageId:"+messageId+"  messageData:"+messageData+"  createTime:"+createTime);
            System.out.println("消费的主题消息来自："+message.getMessageProperties().getConsumerQueue());

            channel.basicAck(deliveryTag, true);//第二个参数，手动确认可以被批处理，当该参数为 true 时，则可以一次性确认 delivery_tag 小于等于传入值的所有消息
//			channel.basicReject(deliveryTag, true);//第二个参数，true会重新放回队列，所以需要自己根据业务逻辑判断什么时候使用拒绝
        } catch (Exception e) {
            channel.basicReject(deliveryTag, false);
        }

    }

    //{key=value,key=value,key=value} 格式转换成map
    private Map<String, String> mapStringToMap(String str, int entryNum) {
        str = str.substring(1, str.length() -1);
        String[] strs = str.split(",", entryNum);
        Map<String, String> map = new HashMap<String, String>();
        for (String s : strs) {
            String key = s.split("=")[0].trim();
            String value = s.split("=")[1];
            map.put(key, value);
        }
        return map;
    }

    @Override
    public void onMessage(Message message) {

    }

    @Override
    public void containerAckMode(AcknowledgeMode mode) {

    }
}
