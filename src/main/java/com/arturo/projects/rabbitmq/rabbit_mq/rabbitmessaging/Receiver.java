package com.arturo.projects.rabbitmq.rabbit_mq.rabbitmessaging;


import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.arturo.projects.rabbitmq.rabbit_mq.domain.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;

@Component
public class Receiver {
 
    private QueueErrorHandler queueErrorHandler;
    private ObjectMapper mapper = new ObjectMapper();

    public Receiver() {
        this.queueErrorHandler = new QueueErrorHandler(
            QueueFactory.PROCESS_NAME_EXCHANGE_DEAD,
            QueueFactory.PROCESS_NAME_QUEUE_DEAD, 
            2 );
        this.mapper  = new ObjectMapper();
    }



    @RabbitListener(queues = QueueFactory.PROCESS_NAME_QUEUE_WORK)
    public void rabbitConsumer(Message message, Channel channel) throws Exception{
        try{
            Order order = mapper.readValue(message.getBody(), Order.class);
        
            //simulates an exception. This shouldnot be the kind of errors handle by this retry logic
            if(null != order && order.getClientId().isEmpty() ){
                queueErrorHandler.processError(message, channel);
            }else{
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
        }catch(Exception ex){
            queueErrorHandler.processError(message, channel);
        }
        
    }
}