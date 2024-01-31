package com.wanted.preonboarding.user.domain.dto;

import com.wanted.preonboarding.user.domain.entity.Payment;
import com.wanted.preonboarding.user.domain.entity.PaymentCard;
import com.wanted.preonboarding.user.domain.entity.User;
import java.sql.Date;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserInfo {
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private Date birthday;
    private List<String> paymentNames;
    private List<Long> balanceAmounts;


    public static UserInfo of(User entity){

        return UserInfo.builder()
            .id(entity.getId())
            .name(entity.getName())
            .email(entity.getEmail())
            .phoneNumber(entity.getPhoneNumber())
            .birthday(entity.getBirthday())
            .paymentNames(entity.getPaymentCards().stream().map(PaymentCard::getCardName).toList())
            .balanceAmounts(entity.getPaymentCards().stream().map(Payment::getBalanceAmount).toList())
            .build();
    }
}
