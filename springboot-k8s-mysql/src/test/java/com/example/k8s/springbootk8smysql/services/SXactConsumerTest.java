package com.example.k8s.springbootk8smysql.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("local")
class SXactConsumerTest {
    @Autowired
    SXactConsumer consumer;

    @Test
    void consumeFromTopics(){

    }
}