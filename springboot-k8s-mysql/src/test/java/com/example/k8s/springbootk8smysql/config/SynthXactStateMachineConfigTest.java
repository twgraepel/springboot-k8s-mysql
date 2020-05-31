package com.example.k8s.springbootk8smysql.config;

import com.example.k8s.springbootk8smysql.entity.SynthXactEvent;
import com.example.k8s.springbootk8smysql.entity.SynthXactState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("local")
class SynthXactStateMachineConfigTest {
    @Autowired
    StateMachineFactory<SynthXactState, SynthXactEvent> factory;

    @Test
    void testNewStateMachine(){
        StateMachine<SynthXactState, SynthXactEvent> sm = factory.getStateMachine(UUID.randomUUID());
        sm.start();
        System.out.println(sm.getState().toString());
        sm.sendEvent(SynthXactEvent.ASSIGN);
        System.out.println(sm.getState().toString());
        sm.sendEvent(SynthXactEvent.COMPLETE);
        System.out.println(sm.getState().toString());
    }

}