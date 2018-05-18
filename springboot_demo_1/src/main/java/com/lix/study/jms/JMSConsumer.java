package com.lix.study.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.lix.study.config.jms.JmsConfig;

/**
 * 消息消费者
 * @author lix
 */
//@Component
public class JMSConsumer {
	
	private static final Logger logger = LoggerFactory.getLogger(JMSConsumer.class);

	@JmsListener(destination = JmsConfig.TOPIC, containerFactory = "jmsListenerContainerTopic")
	public void onTopicMessage(String msg) {
		logger.info("接收到topic消息：{}", msg);
	}

	@JmsListener(destination = JmsConfig.QUEUE, containerFactory = "jmsListenerContainerQueue")
	public void onQueueMessage(String msg) {
		logger.info("接收到queue消息：{}", msg);
	}
	
}
