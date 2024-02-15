package com.wanted.preonboarding.domain.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {
  @Override
  public Timestamp convertToDatabaseColumn(LocalDateTime attribute) {
    return (attribute == null ? null : Timestamp.valueOf(attribute));
  }

  @Override
  public LocalDateTime convertToEntityAttribute(Timestamp timestamp) {
    return (timestamp == null ? null : timestamp.toLocalDateTime());
  }
}
