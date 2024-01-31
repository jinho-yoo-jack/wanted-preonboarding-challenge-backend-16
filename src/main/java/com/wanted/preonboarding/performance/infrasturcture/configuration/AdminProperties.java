package com.wanted.preonboarding.performance.infrasturcture.configuration;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "admin")
@PropertySource("classpath:secrets.yml")
@Getter
public class AdminProperties {

    private String key;
}
