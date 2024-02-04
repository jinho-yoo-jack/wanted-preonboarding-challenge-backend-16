package com.wanted.preonboarding.domain.performance.domain.enums;

import java.util.Arrays;
import java.util.Objects;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

public enum PerformanceType {

    CONCERT,
    EXHIBITION

    ;


    @Converter
    public static class DBConverter implements AttributeConverter<PerformanceType, String> {

        @Override
        public String convertToDatabaseColumn(final PerformanceType e) {
            return Objects.isNull(e) ? null : e.name();
        }

        @Override
        public PerformanceType convertToEntityAttribute(final String name) {

            return Arrays.stream(PerformanceType.values())
                .filter(e->e.name().equals(name))
                .findFirst()
                .orElse(null);
        }
    }
}
