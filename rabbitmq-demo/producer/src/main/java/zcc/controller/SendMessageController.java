package zcc.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import zcc.config.DirectRabbitConfig;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author zcc
 * @ClassName SendMessageController
 * @description 然后写个简单的接口进行消息推送（根据需求也可以改为定时任务等等，具体看需求）
 * @date 2021/10/22 15:00
 * @Version 1.0
 */

@RestController
public class SendMessageController {

    @Autowired
    private RabbitTemplate rabbitTemplate; //使用RabbitTemplate,这提供了接收/发送等等方法

    @GetMapping("/sendDirectMessage")
    public String sendDirectMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "test message , hello !";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);

        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend(DirectRabbitConfig.exchangeName, DirectRabbitConfig.routingKey, map);

        return "OK";
    }

    /* *
     * @description: 测试消息推送回调函数
     * 消息推送到名为‘non-existent-exchange’的交换机上（这个交换机是没有创建没有配置的）
     * @param
     * @return {@link java.lang.String} ①这种情况触发的是 ConfirmCallback 回调函数
     * @throws
     * @author zcc13
     * @date 2021/10/22 15:57
     */
    @GetMapping("/TestMessageAck")
    public String testMessageAck() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "test message , hello !";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);

        rabbitTemplate.convertAndSend("non-existent-exchange",DirectRabbitConfig.routingKey, map);

        return "ok";
    }

    /* *
     * @description:
     * 消息推送到server，找到交换机了，但是没找到队列
     * @param
     * @return {@link java.lang.String}  ②这种情况触发的是 ConfirmCallback和RetrunCallback两个回调函数。
     * @throws 
     * @author zcc13
     * @date 2021/10/22 16:15
     */
    @GetMapping("/TestMessageAck2")
    public String TestMessageAck2() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: lonelyDirectExchange test message ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        rabbitTemplate.convertAndSend("lonelyDirectExchange", "TestDirectRouting", map);
        return "ok";
    }
}
