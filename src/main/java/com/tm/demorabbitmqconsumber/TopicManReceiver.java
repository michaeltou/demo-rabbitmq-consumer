package com.tm.demorabbitmqconsumber;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "topic.man.queue",containerFactory = "ackTaskContainerFactory")
public class TopicManReceiver {

 /*   @RabbitHandler
    public void process(Map testMessage) throws InterruptedException {
        System.out.println("2222222 TopicManReceiver消费者收到消息  : " + testMessage.toString());
        Thread.sleep(3000);
    }*/

    @RabbitHandler
    public void process(@Payload Map message,
                        @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
                        Channel channel,
                        @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey) {
        try {
            System.out.println("222222  TopicManReceiver 消费者收到消息  : "
                    + message.toString()+"routingKey = "+routingKey
                    +routingKey+" ,DELIVERY_TAG =" + deliveryTag);
            Thread.sleep(6000);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {

        }
    }

}
