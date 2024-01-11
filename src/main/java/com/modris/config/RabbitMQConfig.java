package com.modris.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// This Configuration is the exact same as the manager one.
// Except this is needed in case the manager app fails to start at first launch.
// Then this application won't start because no queue can be Rabbit Listened to.

@Configuration
public class RabbitMQConfig {

	@Bean
	public DirectExchange direct() {
		return new DirectExchange("main_exchange");
	}
	
	@Bean
	public Queue queue1() {
		return new Queue("direct_bestmove");
	}
	
	@Bean
	public Binding binding1(DirectExchange direct, Queue queue1) {
		return BindingBuilder.bind(queue1).to(direct).with("");
	}
}