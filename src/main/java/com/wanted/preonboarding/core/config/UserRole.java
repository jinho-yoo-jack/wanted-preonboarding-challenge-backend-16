package com.wanted.preonboarding.core.config;

import lombok.Getter;

@Getter
public enum UserRole {
    USER("ROLE_USER");

    private String value;

    UserRole(String value){
        this.value = value;
    }
}
