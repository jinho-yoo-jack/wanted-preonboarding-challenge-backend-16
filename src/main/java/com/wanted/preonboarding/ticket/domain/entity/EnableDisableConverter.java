package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.AttributeConverter;

public class EnableDisableConverter implements AttributeConverter<Boolean,String> {

    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return (attribute != null && attribute) ? "enable" : "disable";
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return "enable".equalsIgnoreCase(dbData);
    }
}
