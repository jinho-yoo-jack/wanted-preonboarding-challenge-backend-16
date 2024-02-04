package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@NoArgsConstructor
@Embeddable
@Getter
@Setter(AccessLevel.PROTECTED)
public class ReserverInfo {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, name = "phone_number")
    private String phoneNumber;

    @Builder
    public ReserverInfo(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
