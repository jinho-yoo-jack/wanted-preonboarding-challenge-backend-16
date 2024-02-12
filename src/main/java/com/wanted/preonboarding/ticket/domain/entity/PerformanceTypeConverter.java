package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.AttributeConverter;

public class PerformanceTypeConverter implements AttributeConverter<PerformanceType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(PerformanceType attribute) {
        if(attribute == null ){
            return null;
        }
        return attribute.getCategory();
    }

    @Override
    public PerformanceType convertToEntityAttribute(Integer dbData) {
        if(dbData == null){
            return null;
        }
        return PerformanceType.of(dbData);
    }
}
