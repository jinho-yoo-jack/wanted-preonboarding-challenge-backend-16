package com.wanted.preonboarding.user.domain.dto;

import com.wanted.preonboarding.user.domain.entity.PaymentCard;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentSetting {
    private String cardName;
    private String cardNum;
    private String expiredDate;
    private String cvc;

    
}
