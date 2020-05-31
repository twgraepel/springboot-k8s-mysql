package com.example.k8s.springbootk8smysql.config;

import com.example.k8s.springbootk8smysql.entity.SynthXactEvent;
import com.example.k8s.springbootk8smysql.entity.SynthXactState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

@Slf4j
@Configuration
@EnableStateMachineFactory
public class SynthXactStateMachineConfig extends StateMachineConfigurerAdapter <SynthXactState, SynthXactEvent>{

    @Override
    public void configure(StateMachineStateConfigurer<SynthXactState, SynthXactEvent> states) throws Exception {
        states.withStates()
                .initial(SynthXactState.INITIATE)
                .states(EnumSet.allOf(SynthXactState.class))
                .end(SynthXactState.COMPLETE)
                .end(SynthXactState.ABANDON);

    }
    @Override
    public void configure(StateMachineTransitionConfigurer<SynthXactState, SynthXactEvent> transitions) throws Exception{
        transitions
                .withExternal().source(SynthXactState.INITIATE).target(SynthXactState.RUNNING).event(SynthXactEvent.ASSIGN)
                .and().withExternal().source(SynthXactState.RUNNING).target(SynthXactState.RUNNING).event(SynthXactEvent.COMPLETE)
                .and().withExternal().source(SynthXactState.RUNNING).target(SynthXactState.LATE).event(SynthXactEvent.COMPLETE)
                .and().withExternal().source(SynthXactState.RUNNING).target(SynthXactState.COMPLETE).event(SynthXactEvent.COMPLETE)
                .and().withExternal().source(SynthXactState.LATE).target(SynthXactState.LATE).event(SynthXactEvent.COMPLETE)
                .and().withExternal().source(SynthXactState.LATE).target(SynthXactState.ORPHAN).event(SynthXactEvent.COMPLETE)
                .and().withExternal().source(SynthXactState.LATE).target(SynthXactState.COMPLETE).event(SynthXactEvent.COMPLETE)
                .and().withExternal().source(SynthXactState.ORPHAN).target(SynthXactState.ORPHAN).event(SynthXactEvent.COMPLETE)
                .and().withExternal().source(SynthXactState.ORPHAN).target(SynthXactState.ABANDON).event(SynthXactEvent.COMPLETE)
                .and().withExternal().source(SynthXactState.ORPHAN).target(SynthXactState.UNORPHAN).event(SynthXactEvent.COMPLETE)
                .and().withExternal().source(SynthXactState.UNORPHAN).target(SynthXactState.COMPLETE).event(SynthXactEvent.COMPLETE)
                .and().withExternal().source(SynthXactState.UNORPHAN).target(SynthXactState.LATE).event(SynthXactEvent.COMPLETE)
        ;

    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<SynthXactState, SynthXactEvent> config) throws Exception {
        StateMachineListenerAdapter<SynthXactState, SynthXactEvent> adapter = new StateMachineListenerAdapter<>(){
            @Override
            public void stateChanged(State<SynthXactState, SynthXactEvent> from, State<SynthXactState, SynthXactEvent> to){
                log.info(String.format("state changed from: %s to: %s",from,to));
            }
        };
        config.withConfiguration().listener(adapter);
    }

    public Action<SynthXactState, SynthXactEvent> doInitAction() {
        return ctx -> log.info("Init Action");
    }
    public Action<SynthXactState, SynthXactEvent> doAssign() {
        return ctx -> log.info("Assignment to Running Action");
    }



}
