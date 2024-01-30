package com.wanted.preonboarding.performance.domain;

import com.wanted.preonboarding.performance.domain.discount_policy.DiscountPolicy;
import com.wanted.preonboarding.performance.domain.vo.PerformanceType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Performance {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PerformanceType type;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_policy_id")
    private DiscountPolicy discountPolicy;

    public Performance(String name, int price, PerformanceType type,
        DiscountPolicy discountPolicy) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.discountPolicy = discountPolicy;
    }

    public static Performance create(String name, int price, PerformanceType type,
        DiscountPolicy discountPolicy){
        return new Performance(name, price, type, discountPolicy);
    }

    public int calculateFee() {
        int discountAmount = discountPolicy.getDiscountAmount(price);
        return this.price - discountAmount;
    }


    public void changePrice(int price) {
        this.price = price;
    }
}
