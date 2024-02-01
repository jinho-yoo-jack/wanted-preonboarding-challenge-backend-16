package com.wanted.preonboarding.ticket.application.common.util;

import java.security.SecureRandom;
import java.util.Random;

/**
 * 무작위 코드를 생성하는 유틸리티 클래스입니다.
 * <p>
 * 인증 코드 생성, 임시 비밀번호 생성 등에 사용할 수 있습니다.
 */
public final class CodeGenerator {

    private static final String CHARACTERS_FOR_CODE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final Random random = new SecureRandom();

    private CodeGenerator() {
        throw new IllegalStateException("유틸리티 클래스는 인스턴스화할 수 없습니다.");
    }

    /**
     * SecureRandom을 이용하여 무작위 코드를 생성합니다.
     * @param requiredLength 생성할 코드의 길이
     */
    public static String generateRandomCode(final int requiredLength) {
        StringBuilder sb = new StringBuilder(requiredLength);
        for (int i = 0; i < requiredLength; i++) {
            sb.append(CHARACTERS_FOR_CODE.charAt(random.nextInt(CHARACTERS_FOR_CODE.length())));
        }
        return sb.toString();
    }

}
