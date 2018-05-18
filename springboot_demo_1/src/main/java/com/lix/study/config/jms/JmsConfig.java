package com.lix.study.config.jms;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Topic;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

@Configuration
public class JmsConfig {

	// 点对点：消息生产者生产消息发布到queue中，然后消息消费者从queue中取出，并且消费消息。
	public static final String TOPIC = "springboot.topic.test";
	// 发布/订阅：消息生产者（发布）将消息发布到topic中，同时有多个消息消费者（订阅）消费该消息。
	public static final String QUEUE = "springboot.queue.test";

	@Bean
	public Queue queueBean() {
		return new ActiveMQQueue(QUEUE);
	}

	@Bean
	public Topic topicBean() {
		return new ActiveMQTopic(TOPIC);
	}

	// topic模式的ListenerContainer
	@Bean
	public JmsListenerContainerFactory<?> jmsListenerContainerTopic(ConnectionFactory activeMQConnectionFactory) {
		DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
		bean.setPubSubDomain(true);
		bean.setConnectionFactory(activeMQConnectionFactory);
		return bean;
	}

	// queue模式的ListenerContainer
	@Bean
	public JmsListenerContainerFactory<?> jmsListenerContainerQueue(ConnectionFactory activeMQConnectionFactory) {
		DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
		bean.setConnectionFactory(activeMQConnectionFactory);
		return bean;
	}

}
