package com.my.kafka_test.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class TopicConfig {
    @Value("${kafka.read-topic}")
    private String readTopic;
    @Value("${kafka.send-topic}")
    private String sendTopic;

//    @Bean
    public KafkaAdmin.NewTopics topics() {
        final NewTopic[] newTopics = {
                new NewTopic(readTopic, 1, (short) 1),
                new NewTopic(sendTopic, 1, (short) 1)
        };
        final KafkaAdmin.NewTopics newTopics1 = new KafkaAdmin.NewTopics(newTopics);
        return newTopics1;
    }

}
