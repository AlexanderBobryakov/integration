package com.my.kafka_test.listener;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class MainListener {
    private final ReadListener readListener;
    private final SendListener sendListener;
    private final AdminClient adminClient;

    @Value("${kafka.read-topic}")
    private String readTopic;
    @Value("${kafka.send-topic}")
    private String sendTopic;

    @EventListener(ApplicationStartedEvent.class)
    public void run() {
        try {
            adminClient.deleteTopics(Arrays.asList(readTopic, sendTopic));
        } catch (Exception e) {
            e.printStackTrace();
        }
        adminClient.createTopics(Arrays.asList(
                new NewTopic(readTopic, 1, (short) 1),
                new NewTopic(sendTopic, 1, (short) 1)
        ));
        readListener.read();
        sendListener.send();
    }
}
