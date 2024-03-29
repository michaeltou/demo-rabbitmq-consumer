package com.tm.demorabbitmqconsumber;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.tm.common.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component

@Slf4j
public class PriorityReceiver {


    @RabbitListener(queues = "priority.queue",containerFactory = "ackTaskContainerFactory")
    public void process(@Payload Map message,
                        @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
                        Channel channel,
                        @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey

                        ) {

        boolean hasException = false;
        boolean hasBussinessException = false;

        try {
            //记录消息日志
            log.info("start收到消息消息体：{},",
                    JSON.toJSON(message));

            //业务逻辑
             Thread.sleep(1000);


        } catch (Exception e) {
            hasException = true;
            log.info("xxx消息处理发生异常，关键信息是：", e.getMessage(),e);
        }finally {
            //记录消息日志
           // log.info("end 结束处理消息 ，消息体：{},",  JSON.toJSON(message));
            try {
                if(hasException){
                    //requeue：需根据具体场景设置true or false。需要放回队列，requeue设置true, 如果删除消息(false)，
                    channel.basicNack(deliveryTag,false,true);
                }else if(hasBussinessException){
                    //requeue：用于指定消息是否需要放回队列（true）还是删除消息(false)，需根据具体场景设置true or false.
                    channel.basicNack(deliveryTag,false,true);
                }else{
                    //消息被正常处理，给消息中心确认反馈
                    channel.basicAck(deliveryTag, false);
                }
            }catch (IOException e){
                log.error("反馈给消息中心发生异常,异常信息是：{}",e.getMessage(),e);
            }
        }
    }



}
