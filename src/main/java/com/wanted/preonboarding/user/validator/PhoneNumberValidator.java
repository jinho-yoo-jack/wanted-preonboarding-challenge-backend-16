package com.wanted.preonboarding.user.validator;

import java.util.regex.Pattern;

public interface PhoneNumberValidator {

    String PHONE_NUMBER_REGEX = "^[0-9]{3}-[0-9]{4}-[0-9]{4}$";
    Pattern PHONE_NUMBER_PATTERN = Pattern.compile(PHONE_NUMBER_REGEX);
    String ERROR_MESSAGE_FORMAT = "유효하지 않은 휴대폰 번호가 들어왔습니다. [%s]";

    static void checkPhoneNumber(String phoneNumber) {
        if (!PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches()) {
            throw new IllegalArgumentException(String.format(ERROR_MESSAGE_FORMAT, phoneNumber));
        }
    }
}
