package com.strconsumer.consumer.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.strconsumer.consumer.listeners.StrConsumerListener;

@Component
public class ErrorCustomHandler implements KafkaListenerErrorHandler{

	private static Logger logger = LoggerFactory.getLogger(StrConsumerListener.class);
	
	@Override
	public Object handleError(Message<?> message, ListenerExecutionFailedException e) {
		logger.info("EXCEPTION_HANDLER ::: Captured an error");
		logger.info("Payload ::: {}", message.getPayload());
		logger.info("Payload ::: {}", message.getHeaders());
		logger.info("Payload ::: {}", message.getHeaders().get("kafka_offset"));
		logger.info("Message exception ::: {}", e.getMessage());
		return null;
	}
	
}

