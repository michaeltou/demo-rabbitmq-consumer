package com.tm.demorabbitmqconsumber;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {
    @Autowired
    private CachingConnectionFactory connectionFactory;


    private Integer taskConcurrentConsumers= 10 ;


    private Integer taskMaxConcurrentConsumers =10;


    @Bean(name = "ackTaskContainerFactory")
    public SimpleRabbitListenerContainerFactory ackTaskContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(taskConcurrentConsumers);
        factory.setMaxConcurrentConsumers(taskMaxConcurrentConsumers);
        factory.setPrefetchCount(1);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        //factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }

    @Bean(name = "mySimpleTaskContainerFactory")
    public SimpleRabbitListenerContainerFactory ackTaskContainerFactory3() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();

        //factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
         return factory;
    }
}
