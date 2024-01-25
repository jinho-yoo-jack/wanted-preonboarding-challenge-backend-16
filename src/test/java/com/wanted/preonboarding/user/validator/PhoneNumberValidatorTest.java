package com.wanted.preonboarding.user.validator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PhoneNumberValidatorTest {

    @DisplayName("유효한 휴대폰 번호가 들어오면 예외가 발생하지 않는다.")
    @ValueSource(strings = {"010-0000-0000", "011-0000-0000", "019-0000-1111"})
    @ParameterizedTest
    void successPhoneNumberCheck(String phoneNumber) {

        // when & then
        Assertions.assertThatCode(() -> PhoneNumberValidator.checkPhoneNumber(phoneNumber))
                        .doesNotThrowAnyException();
    }

    @DisplayName("유효하지 않은 휴대폰 번호가 들어오면 예외가 발생한다.")
    @ValueSource(strings = {"010-000a-0000", "011-0000-000", "019-0000-11112", "0110-0000-3333"})
    @ParameterizedTest
    void failedPhoneNumberCheck(String phoneNumber) {

        // when & then
        Assertions.assertThatThrownBy(() -> PhoneNumberValidator.checkPhoneNumber(phoneNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format("유효하지 않은 휴대폰 번호가 들어왔습니다. [%s]", phoneNumber));
    }
}