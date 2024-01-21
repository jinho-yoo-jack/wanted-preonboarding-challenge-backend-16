package com.wanted.preonboarding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class BackendPreonboardingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendPreonboardingApplication.class, args);
    }

}
