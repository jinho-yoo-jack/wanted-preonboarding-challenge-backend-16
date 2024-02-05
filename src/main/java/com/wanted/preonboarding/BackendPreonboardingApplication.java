package com.wanted.preonboarding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BackendPreonboardingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendPreonboardingApplication.class, args);
    }

}
