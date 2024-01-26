package com.wanted.preonboarding.reservation.domain.valueObject;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    private String name;

    @Column(nullable = false, name = "phone_number")
    @NotBlank
    private String phoneNumber;
}
