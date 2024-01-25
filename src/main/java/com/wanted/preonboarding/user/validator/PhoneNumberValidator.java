package com.wanted.preonboarding.user.validator;

import java.util.regex.Pattern;

public final class PhoneNumberValidator {

    private static final String PHONE_NUMBER_REGEX = "^[0-9]{3}-[0-9]{4}-[0-9]{4}$";
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(PHONE_NUMBER_REGEX);
    private static final String ERROR_MESSAGE_FORMAT = "유효하지 않은 휴대폰 번호가 들어왔습니다. [%s]";

    private PhoneNumberValidator() {

    }

    public static void checkPhoneNumber(String phoneNumber) {
        if(!PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches()) {
            throw new IllegalArgumentException(String.format(ERROR_MESSAGE_FORMAT, phoneNumber));
        }
    }
}
