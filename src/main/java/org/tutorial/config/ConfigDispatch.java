package org.tutorial.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tutorial.service.ReceiverDispatch;

@Configuration
public class ConfigDispatch {
    private int data = 0;
    @Bean
    public ReceiverDispatch receiverDispatch() {
        synchronized (this){
            this.data = this.data + 1;
        }
        return new ReceiverDispatch(data);
    }
}
