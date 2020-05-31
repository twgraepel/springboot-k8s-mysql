package com.example.k8s.springbootk8smysql.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Slf4j
//@Service
public class TestMessageCreation {
    @Autowired
    SXactProducer producer;

    @Scheduled(fixedDelay=2000 )
    public void makeMeSomeDataMessages() {
        Random r = new Random();
        String s = new Date().toString();
        List<String> pronounList = Arrays.asList("I", "We", "You", "He", "She", "Tom", "Ed", "Maureen");
        List<String> verbList = Arrays.asList("likes", "liked", "hates", "hated", "brought", "is bringing");
        List<String> adjList = Arrays.asList("red", "yellow", "big", "small", "green", "tiny");
        List<String> nounList = Arrays.asList("cookies", "ham", "hammocks", "sleeping bags", "frying pans", "cars", "boats", "pens");

        String myRandomString = String.format("%s %s %s %s.",
                pronounList.get(r.nextInt(pronounList.size())),
                verbList.get(r.nextInt(verbList.size())),
                adjList.get(r.nextInt(adjList.size())),
                nounList.get(r.nextInt(nounList.size())));

        producer.sendMessage(String.format("ID: %d Date: %s: Message: %s",r.nextInt(10000), new Date(), myRandomString));
    }
}
