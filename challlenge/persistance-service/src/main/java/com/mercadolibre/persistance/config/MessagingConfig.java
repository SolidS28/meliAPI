package com.mercadolibre.persistance.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mercadolibre.persistance.reciever.PersistanceReciever;

@Configuration
public class MessagingConfig {

	private static final String PERSITANCE_ITEMS_ROUTING = "persistance.items.#";
	private static final String ITEMS_TOPIC = "items-persistance";
	private static final String QUEUE = "meli-queue";

	@Bean
	Queue queue() {
		return new Queue(QUEUE, false);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(ITEMS_TOPIC);
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(PERSITANCE_ITEMS_ROUTING);
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(QUEUE);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(PersistanceReciever receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}
}
