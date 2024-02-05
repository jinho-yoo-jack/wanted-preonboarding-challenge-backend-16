package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.domain.entity.User;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserInfo {
    private UUID userId;
    private String name;
    private String phoneNumber;
    private String email;

    public static UserInfo of(User entity) {
        return UserInfo.builder()
                .userId(entity.getId())
                .name(entity.getName())
                .phoneNumber(entity.getPhoneNumber())
                .email(entity.getEmail())
                .build();
    }
}
