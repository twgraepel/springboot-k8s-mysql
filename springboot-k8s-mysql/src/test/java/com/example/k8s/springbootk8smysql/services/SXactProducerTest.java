package com.example.k8s.springbootk8smysql.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("local")
class SXactProducerTest {
    @Autowired
    SXactProducer producer;

    @Test
    void putItemOnMyTopic1(){
        Random r = new Random();
        String s = new Date().toString();
        producer.sendMessage("This is my message:" + s + " : " + r.nextInt(10000));
    }

}