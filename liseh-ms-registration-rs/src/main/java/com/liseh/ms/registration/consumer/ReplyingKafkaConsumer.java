package com.liseh.ms.registration.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liseh.GenericKafkaObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:kafka_configs.properties")
public class ReplyingKafkaConsumer {

    private final static Logger logger = LogManager.getLogger(ReplyingKafkaConsumer.class);

    @KafkaListener(topics = "${kafka.request-topic}")
    @SendTo
    public GenericKafkaObject listen(GenericKafkaObject request){
        GenericKafkaObject response = new GenericKafkaObject();
        try {
            ObjectMapper mapper = new ObjectMapper();
            logger.info("Request Object from Registration Topic: " + mapper.writeValueAsString(request));
            response.setContent(request.getContent() + " - Processed by Registration MicroService");
        } catch (JsonProcessingException jpe){
            response.setContent(jpe.getMessage());
        }
        return response;
    }
}
