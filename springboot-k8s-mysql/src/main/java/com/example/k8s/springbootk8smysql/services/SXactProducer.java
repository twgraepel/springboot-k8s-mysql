package com.example.k8s.springbootk8smysql.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
//@NoArgsConstructor
//@AllArgsConstructor
//@RequiredArgsConstructor
public class SXactProducer {
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        //log.info(String.format("#### -> Producing message -> %s", message));
        this.kafkaTemplate.send("mytopic1", message);
        //log.info(String.format("#### -> After Producing message -> %s", message));
    }
}
