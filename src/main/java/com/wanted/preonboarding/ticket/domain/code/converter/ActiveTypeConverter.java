package com.wanted.preonboarding.ticket.domain.code.converter;

import com.wanted.preonboarding.ticket.domain.code.ActiveType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ActiveTypeConverter implements AttributeConverter<ActiveType, String> {
    @Override
    public String convertToDatabaseColumn(ActiveType attribute) {
        return attribute != null ? attribute.getCode() : ActiveType.CLOSE.getCode();
    }

    @Override
    public ActiveType convertToEntityAttribute(String dbData) {
        return dbData != null ? ActiveType.fromCode(dbData) : ActiveType.CLOSE;
    }
}
