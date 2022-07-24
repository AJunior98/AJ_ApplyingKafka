package com.strproducer.producer.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class StringProducerService {	
	
	private static Logger logger = LoggerFactory.getLogger(StringProducerService.class);
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
		
	public void sendMessage(String message) {
		kafkaTemplate.send("str-topic", message).addCallback(
				success -> {
					if(success != null) {
						logger.info("Send message with success {}", message);
						logger.info("Partition {}, Offset{}", 
								success.getRecordMetadata().partition(),
								success.getRecordMetadata().offset());
					}
				},
				error -> System.out.println("Error send message")
				);
	}
}
