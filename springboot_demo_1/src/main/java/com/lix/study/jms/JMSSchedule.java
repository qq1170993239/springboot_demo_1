package com.lix.study.jms;

import javax.jms.Queue;
import javax.jms.Topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lix.study.sdk.common.utils.DateUtils;

/**
 * activemq生产消息
 * @author lix
 */
// http://localhost:8161/admin/
@Component
public class JMSSchedule {
	
	private static final Logger logger = LoggerFactory.getLogger(JMSSchedule.class);

	@Autowired
	private JMSProducer jmsProducer;
	
	@Autowired
	private Topic topic;
	
	@Autowired
	private Queue queue;

	// 10-50秒之间执行
//	@Scheduled(fixedRate = 5000)
	public void producer() {
		
		logger.info("生产了一组消息！");

		// queue消息生产
		jmsProducer.sendMessage(queue, "queue,world!" + DateUtils.getCurrentTime());

		// topic消息生产
		jmsProducer.sendMessage(topic, "topic,world!" + DateUtils.getCurrentTime());
	}
}
