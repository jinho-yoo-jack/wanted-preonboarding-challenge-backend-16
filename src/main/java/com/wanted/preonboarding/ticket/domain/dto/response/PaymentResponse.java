package com.wanted.preonboarding.ticket.domain.dto.response;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class PaymentResponse {

    Integer paidPrice;
    List<String> discountDetails;
    Integer remainBalance;

}
