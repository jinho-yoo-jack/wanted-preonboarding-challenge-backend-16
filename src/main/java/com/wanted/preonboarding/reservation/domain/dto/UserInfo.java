package com.wanted.preonboarding.reservation.domain.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
@Getter
public class UserInfo {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, name = "phone_number")
    private String phoneNumber;
}
