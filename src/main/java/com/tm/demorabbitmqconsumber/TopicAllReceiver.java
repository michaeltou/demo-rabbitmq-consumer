package com.tm.demorabbitmqconsumber;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.rabbitmq.client.Channel;
import com.rabbitmq.tools.json.JSONUtil;
import com.tm.common.model.User;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "topic.all.queue",containerFactory = "ackTaskContainerFactory")
public class TopicAllReceiver {

  //  @RabbitHandler
//    public void process(Map testMessage) throws InterruptedException {
//        System.out.println("111111111TopicAllReceiver 消费者收到消息  : " + testMessage.toString());
//        Thread.sleep(3000);
//    }

   /* @RabbitHandler
    public void process(@Payload Map message,
                        @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
                        Channel channel,
                        @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey) {
        try {
            System.out.println("111111111 TopicAllReceiver 消费者收到消息  : "
                    + message.toString()+"routingKey = "
                    +routingKey+" ,DELIVERY_TAG =" + deliveryTag);
            Thread.sleep(6000);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {

        }
    }
*/

    @RabbitHandler
    public void process(@Payload User user,
                        @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
                        Channel channel,
                        @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey) {
        try {
            System.out.println("111111111 TopicAllReceiver 消费者收到消息  : "
                    + JSON.toJSONString(user) +"routingKey = "
                    +routingKey+" ,DELIVERY_TAG =" + deliveryTag);
            Thread.sleep(6000);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {

        }
    }
}
