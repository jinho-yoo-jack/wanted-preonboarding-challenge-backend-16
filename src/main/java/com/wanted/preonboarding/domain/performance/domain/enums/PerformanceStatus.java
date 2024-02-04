package com.wanted.preonboarding.domain.performance.domain.enums;

import java.util.Arrays;
import java.util.Objects;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PerformanceStatus {

    ON_GOING(true),
    QUIT(false)

    ;


    private final boolean availableToReserveYn;

    @Converter
    public static class DBConverter implements AttributeConverter<PerformanceStatus, String> {

        @Override
        public String convertToDatabaseColumn(final PerformanceStatus e) {
            return Objects.isNull(e) ? null : e.name();
        }

        @Override
        public PerformanceStatus convertToEntityAttribute(final String name) {

            return Arrays.stream(PerformanceStatus.values())
                .filter(e->e.name().equals(name))
                .findFirst()
                .orElse(null);
        }
    }
}
