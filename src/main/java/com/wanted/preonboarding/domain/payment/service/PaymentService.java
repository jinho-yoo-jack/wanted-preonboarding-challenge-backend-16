package com.wanted.preonboarding.domain.payment.service;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PaymentService {


    // TODO PG 서비스 연동
    // TODO 각종 할인 적용 가능
    @Transactional
    public void pay(
        Long money, Long amount) {

        if(Objects.isNull(money) || money < 0) {

            throw new IllegalArgumentException("money");
        }
        if(Objects.isNull(amount) || amount < 0) {

            throw new IllegalArgumentException("amount");
        }
        if(money < amount) {

            throw new IllegalArgumentException("money < amount");
        }
    }


    // TODO PG 서비스 연동
    @Transactional
    public void cancel(Long amount) {

        if(Objects.isNull(amount) || amount < 0) {

            throw new IllegalArgumentException("amount");
        }
    }

}
