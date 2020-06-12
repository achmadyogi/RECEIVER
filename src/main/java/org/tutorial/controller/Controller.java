package org.tutorial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.tutorial.config.Config;
import org.tutorial.config.ConfigDispatch;

import javax.annotation.PostConstruct;

public class Controller {
    @Autowired
    Config config;

    @Autowired
    ConfigDispatch configDispatch;

    @PostConstruct
    public void receiving(){
        config.receiver();
        configDispatch.receiverDispatch();
        configDispatch.receiverDispatch();
    }
}
