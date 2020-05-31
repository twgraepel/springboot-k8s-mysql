package com.example.k8s.springbootk8smysql.controller;

import com.example.k8s.springbootk8smysql.entity.Widget;
import com.example.k8s.springbootk8smysql.repository.WidgetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Random;

import java.util.List;
@Slf4j
//@RestController
public class WidgetController {
    @Autowired
    private WidgetRepository widgetRepository;

    @GetMapping("/widget")
    public Widget makeUpAWidget() {
        Widget myWidget = Widget.builder().name("foo").id(1).build();
        return myWidget;
    }

    //@Scheduled(fixedRate=5000)
    public void RunMe() {
        List<String> givenList = Arrays.asList("IT", "HR", "FINANCE", "RECEIVING", "SHIPPING", "QUALITY");
        Random rand = new Random();
        String randomElement = givenList.get(rand.nextInt(givenList.size()));

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;

        String generatedString = rand.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        Widget myWidget = Widget.builder()
                .name(generatedString)
                .department(randomElement)
                .build();
        widgetRepository.save(myWidget);
        log.info("Widget: {}", myWidget);
    }


}
