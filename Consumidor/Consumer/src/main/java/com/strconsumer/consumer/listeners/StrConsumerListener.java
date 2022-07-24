package com.strconsumer.consumer.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class StrConsumerListener {
	
	private static Logger logger = LoggerFactory.getLogger(StrConsumerListener.class);
	
	@KafkaListener(groupId = "group-1", topics = "str-topic", containerFactory = "strContainerFactory")
	public void listener(String message) {
		logger.info("Receive message {}", message);
	}
}
