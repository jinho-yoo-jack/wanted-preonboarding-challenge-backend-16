package com.wanted.preonboarding.ticket.unit.util;

import com.wanted.preonboarding.ticket.application.common.util.CodeGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class CodeGeneratorTest {

    @Test
    @DisplayName("랜덤 코드 생성 성공")
    void shouldGenerateRandomCode() {
        // Given
        int requiredLength = 6;

        // When
        String randomCode = CodeGenerator.generateRandomCode(requiredLength);

        // Then
        assertThat(randomCode).hasSize(requiredLength);
    }

    @Test
    @DisplayName("랜덤 코드 생성 실패 - 요청 길이가 음수인 경우")
    void shouldFailToGenerateRandomCodeNegativeLength() {
        // Given
        int requiredLength = -1;

        // When & Then
        assertThatThrownBy(() -> CodeGenerator.generateRandomCode(requiredLength))
                .isInstanceOf(NegativeArraySizeException.class);
    }
}
