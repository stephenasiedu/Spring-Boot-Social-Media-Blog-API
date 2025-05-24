package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import static org.springframework.boot.SpringApplication.run;

/**
 * Main application class for starting the Social Media API.
 */
@SpringBootApplication
public class SocialMediaApp {
    /**
     * Entry point for the application.
     * @param args The arguments of the program.
     * @throws InterruptedException if the application is interrupted
     */
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(SocialMediaApp.class, args);
    }
}