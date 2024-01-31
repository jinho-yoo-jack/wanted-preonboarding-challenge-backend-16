package com.wanted.preonboarding.user.domain.dto;

import com.wanted.preonboarding.user.domain.entity.PaymentCard;
import com.wanted.preonboarding.user.domain.entity.User;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

/**
 * UUID userUUID: 유저 UUID
 * String name: 유저 이름
 * String id: 유저 ID(계정)
 * String phoneNumber: 유저 휴대전화 번호
 * List< ? > payments: 결제 수단 리스트
 */
@Getter
@Builder
public class UserAndPaymentInfo {
    private UUID userUuid;
    private String name;
    private String id;
    private String phoneNumber;
    private PaymentCard paymentCard;

    public static UserAndPaymentInfo of(User entity, PaymentCard paymentCard){
        return UserAndPaymentInfo.builder()
            .userUuid(entity.getUserUuid())
            .name(entity.getName())
            .id(entity.getId())
            .phoneNumber(entity.getPhoneNumber())
            .paymentCard(paymentCard)
            .build();
    }
}
