package com.github.apycazo.api.gateway.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApp
{
    public static void main (String [] args)
    {
        new SpringApplication(DemoApp.class).run(args);
    }
}
