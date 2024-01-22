package com.wanted.preonboarding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // @EntityListeners(AuditingEntityListener.class) 가 작동하도록 허용
public class BackendPreonboardingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendPreonboardingApplication.class, args);
    }

}
