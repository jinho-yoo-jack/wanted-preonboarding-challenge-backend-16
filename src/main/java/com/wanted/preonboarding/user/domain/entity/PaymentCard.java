package com.wanted.preonboarding.user.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Table
@Getter
@SuperBuilder   // 부모 클래스의 필드까지 빌더 패턴에 포함시키기 위함
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PaymentCard extends Payment{
    @Column(nullable = false)
    private String cardNum;
    @Column(nullable = false)
    private String expiredDate;
    @Column(nullable = false)
    private String cvc;

}
