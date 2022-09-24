package com.my.kafka_test.listener;

import com.my.kafka_test.model.CustomType;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class SendListener {
    private final KafkaTemplate<String, CustomType> kafkaTemplate;
//    private final KafkaProducer<Object, CustomType> kafkaProducer;

    @Value("${kafka.read-topic}")
    private String readTopic;
    @Value("${kafka.send-topic}")
    private String sendTopic;

    @Async
    public void send() {
        while (true) {
            try {
//                final String message = new JSONObject().put("key", LocalDateTime.now().getSecond()).toString();
                final CustomType message = new CustomType("name_" + LocalDateTime.now().getSecond());
                final ProducerRecord<String, CustomType> record = new ProducerRecord<>(readTopic, message);

//                final Future<RecordMetadata> send = kafkaProducer.send(record);
//                final RecordMetadata recordMetadata1 = send.get();
//                System.out.println("SEND_ " + recordMetadata1.offset());

                final ListenableFuture<SendResult<String, CustomType>> send = kafkaTemplate.send(record);
                System.out.println("SEND_ " + send.get().toString());

                Thread.sleep(5000L);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
