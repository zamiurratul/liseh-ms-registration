package com.liseh;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@PropertySource("classpath:kafka_configs.properties")
public class KafkaConsumerConfiguration {
    @Value("${kafka.bootstrap-servers}")
    private String bootStrapServers;

    @Value("${kafka.consumer-group}")
    private String consumerGroup;

    @Value("${kafka.reply-topic}")
    private String replyTopic;

    @Autowired
    private ProducerFactory<String, GenericKafkaObject> producerFactory;

    private Map<String, Object> consumerConfigs(){
        Map<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroup);
//        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return configs;
    }

    @Bean
    public ConsumerFactory<String, GenericKafkaObject> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new StringDeserializer(), new JsonDeserializer<>(GenericKafkaObject.class));
    }

    @Bean
    public KafkaMessageListenerContainer<String, GenericKafkaObject> replyContainer(ConsumerFactory<String, GenericKafkaObject> consumerFactory){
        ContainerProperties containerProperties = new ContainerProperties(replyTopic);
        return new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
    }

    @Bean
    public ReplyingKafkaTemplate<String, GenericKafkaObject, GenericKafkaObject> kafkaReplyTemplate(ProducerFactory<String, GenericKafkaObject> producerFactory, KafkaMessageListenerContainer<String, GenericKafkaObject> container){
        return new ReplyingKafkaTemplate<>(producerFactory, container);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, GenericKafkaObject> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, GenericKafkaObject> containerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        containerFactory.setConsumerFactory(consumerFactory());
        containerFactory.setReplyTemplate(kafkaReplyTemplate(producerFactory, replyContainer(consumerFactory())));
        return containerFactory;
    }
}
