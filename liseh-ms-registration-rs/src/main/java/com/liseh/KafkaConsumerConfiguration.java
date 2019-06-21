/*
package com.liseh;

import com.liseh.GenericKafkaObject;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;

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

    @Bean
    public ConsumerFactory<String, GenericKafkaObject> consumerFactory() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroup);
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(configs);
    }

    @Bean
    public KafkaMessageListenerContainer<String, GenericKafkaObject> replyContainer(ConsumerFactory<String, GenericKafkaObject> consumerFactory){
        String[] topics = new String[1];
        topics[0] = replyTopic;
        ContainerProperties containerProperties = new ContainerProperties(topics);
        return new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
    }

    @Bean
    public ReplyingKafkaTemplate<String, GenericKafkaObject, GenericKafkaObject> kafkaReplyTemplate(ProducerFactory<String, GenericKafkaObject> producerFactory, KafkaMessageListenerContainer<String, GenericKafkaObject> container){
        return new ReplyingKafkaTemplate<>(producerFactory, container);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, GenericKafkaObject> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, GenericKafkaObject> containerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        containerFactory.setReplyTemplate(kafkaReplyTemplate(producerFactory, replyContainer(consumerFactory())));
        return containerFactory;
    }
}
*/
