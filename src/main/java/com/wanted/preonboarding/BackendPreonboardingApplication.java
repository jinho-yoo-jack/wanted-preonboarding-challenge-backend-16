package com.wanted.preonboarding;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.presentation.QueryController;
import com.wanted.preonboarding.ticket.presentation.ReserveController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;

import javax.management.Query;
import java.util.List;

@SpringBootApplication
public class BackendPreonboardingApplication {

    public static void main(String[] args) {

        SpringApplication.run(BackendPreonboardingApplication.class, args);

    }

}
