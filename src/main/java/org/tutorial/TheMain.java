package org.tutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"org.tutorial"})
public class TheMain {
    public static void main(String[] args){
        SpringApplication.run(TheMain.class, args);
    }
}
