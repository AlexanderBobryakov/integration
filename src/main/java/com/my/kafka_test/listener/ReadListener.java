package com.my.kafka_test.listener;

import com.my.kafka_test.model.CustomType;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ReadListener {
    private final KafkaConsumer<Object, CustomType> consumer;

    @Value("${kafka.read-topic}")
    private String readTopic;
    @Value("${kafka.send-topic}")
    private String sendTopic;

    @Async
    public void read() {
        consumer.subscribe(Collections.singleton(readTopic));
        while (true) {
            try {
                final ConsumerRecords<Object, CustomType> records = consumer.poll(Duration.ofMillis(5000));
//                System.out.println("READ_ " + StreamSupport.stream(records.spliterator(), false)
//                        .map(ConsumerRecord::value).collect(Collectors.joining("\n")));
                System.out.println("READ_ " + StreamSupport.stream(records.spliterator(), false)
                        .map(ConsumerRecord::value).map(Object::toString).collect(Collectors.joining("\n")));
                consumer.commitSync();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
