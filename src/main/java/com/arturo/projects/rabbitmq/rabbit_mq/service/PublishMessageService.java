package com.arturo.projects.rabbitmq.rabbit_mq.service;

import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.arturo.projects.rabbitmq.rabbit_mq.rabbitmessaging.Receiver;

@Service
public class PublishMessageService {

    private final RabbitTemplate rabbitTemplate;

    @Value("com.arturo.projects.rabbitmq.config.queue.exchange")
    private final String TOPIC_EXCHANGE = "";

    @Value("com.arturo.projects.rabbitmq.config.queue.routing")
    private final String ROUTING_KEY = "";

    public PublishMessageService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishMessage(String message){
        rabbitTemplate.convertAndSend(this.TOPIC_EXCHANGE, this.ROUTING_KEY, message);
	
    }
}
