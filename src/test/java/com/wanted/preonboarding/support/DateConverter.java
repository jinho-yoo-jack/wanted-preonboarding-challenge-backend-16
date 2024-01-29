package com.wanted.preonboarding.support;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface DateConverter {

    static String convertLocalDateTimeToString(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }
}
