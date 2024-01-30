package com.wanted.preonboarding.ticket.domain.performance.model;

import jakarta.persistence.AttributeConverter;

public class ReserveStateConverter implements AttributeConverter<ReserveState, String> {

    @Override
    public String convertToDatabaseColumn(ReserveState attribute) {
        if (attribute == null) return null;
        return attribute.getCommand();
    }

    @Override
    public ReserveState convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        return ReserveState.convertToEnum(dbData);
    }
}
