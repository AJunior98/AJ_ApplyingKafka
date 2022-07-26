package com.strconsumer.consumer.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.strconsumer.consumer.custom.StrConsumerCustomListener;

import lombok.SneakyThrows;

@Component
public class StrConsumerListener {
	
	private static Logger logger = LoggerFactory.getLogger(StrConsumerListener.class);
	
	@SneakyThrows
	@StrConsumerCustomListener(groupId = "group-1")
	public void create(String message) {
		logger.info("CREATE ::: Receive message {}", message);
		throw new IllegalArgumentException("EXCEPTION...");
	}
	
	@StrConsumerCustomListener(groupId = "group-1")
	public void log(String message) {
		logger.info("LOG ::: Receive message {}", message);
	}
	
	@KafkaListener(groupId = "group-2", topics = "str-topic", containerFactory = "validMessageContainerFactory")
	public void history(String message) {
		logger.info("HISTORY ::: Receive message {}", message);
	}
}
