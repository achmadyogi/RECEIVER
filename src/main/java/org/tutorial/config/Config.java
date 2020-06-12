package org.tutorial.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tutorial.service.Receiver;

@Configuration
public class Config {
    @Bean
    public FanoutExchange fanout() {
        return new FanoutExchange("tut.fanout");
    }

    @Bean
    public Queue autoDeleteQueue1() {
        return new AnonymousQueue();
    }

    @Bean
    public Queue autoDeleteQueue2() {
        return new AnonymousQueue();
    }

    @Bean
    public Binding binding1(FanoutExchange fanout, Queue autoDeleteQueue1) {
        return BindingBuilder.bind(autoDeleteQueue1).to(fanout);
    }

    @Bean
    public Binding binding2(FanoutExchange fanout, Queue autoDeleteQueue2) {
        return BindingBuilder.bind(autoDeleteQueue2).to(fanout);
    }

    @Bean
    public Receiver receiver() {
        return new Receiver();
    }
}
