package com.wanted.preonboarding.ticket.domain.discount.model;

import com.wanted.preonboarding.ticket.domain.performance.model.PerformanceType;
import jakarta.persistence.AttributeConverter;

public class DiscountTypeConverter implements AttributeConverter<DiscountType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(DiscountType attribute) {
        if (attribute == null) return null;
        return attribute.getIndex();
    }

    @Override
    public DiscountType convertToEntityAttribute(Integer dbData) {
        return DiscountType.convertToEnum(dbData);
    }
}
