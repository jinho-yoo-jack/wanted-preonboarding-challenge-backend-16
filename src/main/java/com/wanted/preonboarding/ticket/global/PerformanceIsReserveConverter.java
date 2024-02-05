package com.wanted.preonboarding.ticket.global;

import com.wanted.preonboarding.ticket.domain.PerformanceIsReserve;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PerformanceIsReserveConverter implements Converter<String, PerformanceIsReserve> {

    @Override
    public PerformanceIsReserve convert(String source) {
        return PerformanceIsReserve.valueOf(source.toUpperCase());
    }
}
