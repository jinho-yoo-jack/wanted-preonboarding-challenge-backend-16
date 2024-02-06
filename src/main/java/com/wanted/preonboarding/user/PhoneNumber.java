package com.wanted.preonboarding.user;

import com.wanted.preonboarding.user.validator.PhoneNumberValidator;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class PhoneNumber {

    @Column(nullable = false, name = "phone_number", unique = true)
    private String phoneNumber;

    @Builder
    private PhoneNumber(String phoneNumber) {
        PhoneNumberValidator.checkPhoneNumber(phoneNumber);
        this.phoneNumber = phoneNumber;
    }

    public static PhoneNumber from(String phoneNumber) {
        return PhoneNumber.builder()
                .phoneNumber(phoneNumber)
                .build();
    }
}
