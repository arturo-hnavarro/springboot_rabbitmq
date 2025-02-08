package com.arturo.projects.rabbitmq.rabbit_mq.rabbitmessaging;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;

import com.rabbitmq.client.Channel;

import io.micrometer.common.lang.NonNull;

public class QueueErrorHandler {

    @NonNull
    private String deadExchange;
    @NonNull
    private String routingKeyDeadExchange;
    private int maxAttempts = 5;

    private static final Logger log = LoggerFactory.getLogger(QueueErrorHandler.class);
    
    public QueueErrorHandler(String deadExchange, String routingKeyDeadExchange) {
        this.deadExchange = deadExchange;
        this.routingKeyDeadExchange = routingKeyDeadExchange;
    }


    public QueueErrorHandler(String deadExchange, String routingKeyDeadExchange, int maxAttempts) {
        this(deadExchange, routingKeyDeadExchange);
        this.maxAttempts = maxAttempts;
    }

    public void processError(Message message, Channel channel){
        try{
            if(shouldRequeMessage(message)){
                log.debug("[Message REQUEUE] at " + new Date() + "Message " + message);

				channel.basicReject(message.getMessageProperties().getDeliveryTag(), 
                false);
            }else{
                log.warn("[Message sent to DEAD QUEUE] " + new Date() + " for message " + message);

				channel.basicPublish(this.deadExchange, this.routingKeyDeadExchange,
						null, message.getBody());
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }

        }catch(Exception ex){
            log.error("REQUEUE error: Dead Exchange " + deadExchange, ex);
        }
    }

    private boolean shouldRequeMessage(Message message){
        List<Map<String, ?>> xDeathHeader = message.getMessageProperties().getXDeathHeader();
        if (xDeathHeader != null && xDeathHeader.size() >= 1) {
            Long count = (Long) xDeathHeader.get(0).get("count");
            return count < maxAttempts;
        }
        return true;

    }





}
