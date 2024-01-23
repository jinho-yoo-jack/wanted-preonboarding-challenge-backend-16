package com.wanted.preonboarding.ticket.infra.entity

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class EnableDisableConverter : AttributeConverter<Boolean, String> {
    override fun convertToDatabaseColumn(attribute: Boolean): String {
        return if (attribute) "enable" else "disable"
    }

    override fun convertToEntityAttribute(dbData: String): Boolean {
        return dbData == "enable"
    }
}
