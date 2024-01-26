package com.wanted.preonboarding.reservation.infrastructure.configuration;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "twilio")
@PropertySource("classpath:secrets.yml")
@Getter
public class TwilioProperties {

    private String username;
    private String password;
    private String phone;
}
