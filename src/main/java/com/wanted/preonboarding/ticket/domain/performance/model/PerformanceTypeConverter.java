package com.wanted.preonboarding.ticket.domain.performance.model;

import jakarta.persistence.AttributeConverter;

public class PerformanceTypeConverter implements AttributeConverter<PerformanceType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(PerformanceType attribute) {
        if (attribute == null) return null;
        return attribute.getIndex();
    }

    @Override
    public PerformanceType convertToEntityAttribute(Integer dbData) {
        return PerformanceType.convertToEnum(dbData);
    }
}
