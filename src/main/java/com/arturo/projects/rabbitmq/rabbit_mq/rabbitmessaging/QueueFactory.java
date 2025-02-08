package com.arturo.projects.rabbitmq.rabbit_mq.rabbitmessaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueFactory {

	public static final String SUFIX_WORK = "work";
	public static final String SUFIX_WAIT = "wait";
	public static final String SUFIX_DEAD = "dead";
	public static final String PREFIX_EXCHANGE = "x";
	public static final String PREFIX_QUEUE = "q";
	public static final String SUFFIX_ID_QUEUE = "listener";

	public static final String PROCESS_NAME = "order.queue.demo";
	public static final String PROCESS = "%s." + PROCESS_NAME + ".%s";

	public static final String PROCESS_NAME_EXCHANGE_WORK = String.format(PROCESS, PREFIX_EXCHANGE, SUFIX_WORK);
	public static final String PROCESS_NAME_EXCHANGE_WAIT = String.format(PROCESS, PREFIX_EXCHANGE, SUFIX_WAIT);
	public static final String PROCESS_NAME_EXCHANGE_DEAD = String.format(PROCESS, PREFIX_EXCHANGE, SUFIX_DEAD);

	public static final String PROCESS_NAME_QUEUE_WORK = PREFIX_QUEUE + "." + PROCESS_NAME + "." + SUFIX_WORK;
	public static final String PROCESS_NAME_QUEUE_WAIT = String.format(PROCESS, PREFIX_QUEUE, SUFIX_WAIT);
	public static final String PROCESS_NAME_QUEUE_DEAD = String.format(PROCESS, PREFIX_QUEUE, SUFIX_DEAD);

	private int retryTime = 1000;

	//Creacion de colas
	@Bean
	Queue workQueue() {
		return QueueBuilder.durable(PROCESS_NAME_QUEUE_WORK)
				.withArgument("x-dead-letter-exchange", PROCESS_NAME_EXCHANGE_WAIT)
				.withArgument("x-dead-letter-routing-key", PROCESS_NAME_QUEUE_WAIT).build();
	}
	
	@Bean
	Queue waitQueue() {
		return QueueBuilder.durable(PROCESS_NAME_QUEUE_WAIT)
				.withArgument("x-dead-letter-exchange", PROCESS_NAME_EXCHANGE_WORK)
				.withArgument("x-message-ttl", retryTime) 
				.withArgument("x-dead-letter-routing-key", PROCESS_NAME_QUEUE_WORK).build();
	}
	@Bean
	Queue deadQueue() {
		return new Queue(PROCESS_NAME_QUEUE_DEAD,true);
	}
	
	@Bean
    Exchange workExchange() {
		return new DirectExchange(PROCESS_NAME_EXCHANGE_WORK);
	}
	
	@Bean
    Exchange waitExchange() {
		return new DirectExchange(PROCESS_NAME_EXCHANGE_WAIT);
	}
	
	@Bean
    Exchange deadExchange() {
		return new DirectExchange(PROCESS_NAME_EXCHANGE_DEAD);
	}

	@Bean
    Binding workBinding(Queue workQueue, DirectExchange workExchange) {
        return BindingBuilder.bind(workQueue).to(workExchange).with(PROCESS_NAME_QUEUE_WORK);
    }
	
	@Bean
    Binding waitBinding(Queue waitQueue, DirectExchange waitExchange) {
        return BindingBuilder.bind(waitQueue).to(waitExchange).with(PROCESS_NAME_QUEUE_WAIT);
    }
	
	@Bean
    Binding deadBinding(Queue deadQueue, DirectExchange deadExchange) {
        return BindingBuilder.bind(deadQueue).to(deadExchange).with(PROCESS_NAME_QUEUE_DEAD);
    }
}
