package com.strconsumer.consumer.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.strconsumer.consumer.custom.StrConsumerCustomListener;

@Component
public class StrConsumerListener {
	
	private static Logger logger = LoggerFactory.getLogger(StrConsumerListener.class);
	
	@StrConsumerCustomListener(groupId = "group-1")
	public void create(String message) {
		logger.info("CREATE ::: Receive message {}", message);
	}
	
	@StrConsumerCustomListener(groupId = "group-1")
	public void log(String message) {
		logger.info("LOG ::: Receive message {}", message);
	}
	
	@StrConsumerCustomListener(groupId = "group-2")
	public void history(String message) {
		logger.info("HISTORY ::: Receive message {}", message);
	}
}
