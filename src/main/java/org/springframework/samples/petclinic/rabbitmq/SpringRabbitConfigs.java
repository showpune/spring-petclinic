package org.springframework.samples.petclinic.rabbitmq;

import org.springframework.amqp.core.*;
// import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
// import org.springframework.amqp.rabbit.connection.ConnectionFactory;
// import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
// import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
// import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringRabbitConfigs {

	private static final String EXCHANGE_NAME = "fanout.ex";

	private static final String QUEUE_NAME = "queue.ex";

	@Bean
	public Queue createQueue() {
		// For learning purpose - durable=false,
		// in a real project you may need to set this as true.
		return new Queue(QUEUE_NAME, true);
	}

	@Bean
	public Exchange fanoutExchange() {
		// durable=true, autoDelete=false
		return new FanoutExchange(EXCHANGE_NAME, true, false);
	}

	@Bean
	public Binding queueBinding() {
		return new Binding(QUEUE_NAME, Binding.DestinationType.QUEUE, EXCHANGE_NAME, "", null);
	}

	// @Bean
	// public ConnectionFactory connectionFactory() {
	// 	CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
	// 	connectionFactory.setUsername("guest");
	// 	connectionFactory.setPassword("guest");
	// 	return connectionFactory;
	// }

	// @Bean
	// public MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory, Queue queue,
	// 		MessageListenerAdapter messageListener) {
	// 	SimpleMessageListenerContainer messageListenerContainer = new SimpleMessageListenerContainer();
	// 	messageListenerContainer.setConnectionFactory(connectionFactory);
	// 	messageListenerContainer.setQueues(queue);
	// 	messageListenerContainer.setMessageListener(messageListener);
	// 	return messageListenerContainer;
	// }

	// @Bean
	// MessageListenerAdapter listenerAdapter(Receiver receiver) {
	// 	return new MessageListenerAdapter(receiver, "receiveMessage");
	// }

}
