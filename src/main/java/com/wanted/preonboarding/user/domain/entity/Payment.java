package com.wanted.preonboarding.user.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass   // 단순히 공통 속성 모음이기 때문에 엔티티화 시키지 않는다
public abstract class Payment {
    @Id
    private Long id;    // 결제 수단 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_uuid", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User userInfo;  // ManyToOne 양방향으로 설정 // 유저 정보
    @Column(nullable = false)
    private Long balanceAmount;    // 결제 수단 잔액

}
