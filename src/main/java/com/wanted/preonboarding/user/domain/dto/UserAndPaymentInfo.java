package com.wanted.preonboarding.user.domain.dto;

import com.wanted.preonboarding.user.domain.entity.User;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserAndPaymentInfo {
    private UUID userUuid;
    private String name;
    private String id;
    private String phoneNumber;
    private List<?> payments;

    public static UserAndPaymentInfo of(User entity, List<?> payments){
        return UserAndPaymentInfo.builder()
            .userUuid(entity.getUserUuid())
            .name(entity.getName())
            .id(entity.getId())
            .phoneNumber(entity.getPhoneNumber())
            .payments(payments)
            .build();
    }
}
