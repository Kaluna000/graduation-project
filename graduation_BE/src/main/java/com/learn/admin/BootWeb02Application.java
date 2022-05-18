package com.learn.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BootWeb02Application {
    public static ConfigurableApplicationContext appContext;
    public static void main(String[] args) {
        appContext = SpringApplication.run(BootWeb02Application.class, args);
    }

}
