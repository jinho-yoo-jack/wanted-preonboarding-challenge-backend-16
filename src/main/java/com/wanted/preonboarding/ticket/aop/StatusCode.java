package com.wanted.preonboarding.ticket.aop;

public enum StatusCode {
    SUCCESS(200, Message.SUCCESS),
    UNAUTHORIZED(401, Message.UNAUTHORIZED),
    NO_AUTH(403, Message.NO_AUTH),
    BAD_REQUEST(404, Message.BAD_REQUEST),
    NOT_FOUND(405, Message.NOT_FOUND),
    ALREADY_EXISTS(409, Message.ALREADY_EXISTS),
    ALREADY_EXISTS_RESERVATION(409, Message.ALREADY_EXISTS_RESERVATION),

    INTERNAL_ERROR(500, Message.INTERNAL_ERROR),
    VALID_NOT_PHONE_NUM(1_007, Message.VALID_NOT_PHONE_NUM),
    VALID_NOT_PASSWORD(1_008, Message.VALID_NOT_PASSWORD),
    MEMBER_NOT_EXIST(1_012, Message.MEMBER_NOT_EXIST),
    LOGIN_REQUIRED(1_019, Message.LOGIN_REQUIRED),
    PARAM_NOT_VALID(400, Message.PARAM_NOT_VALID),
    VALID_NOT_NULL(400, Message.VALID_NOT_NULL),
    VALID_NOT_REGEXP(400, Message.VALID_NOT_REGEXP),
    UNSUPPORTED_ENCODING(400, Message.UNSUPPORTED_ENCODING),
    NO_SUCH_ALGORITHM(500, Message.NO_SUCH_ALGORITHM),
    URI_SYNTAX(400, Message.URI_SYNTAX),
    INVALID_KEY(500, Message.INVALID_KEY),
    JSON_PROCESSING(400, Message.JSON_PROCESSING),
    RESERVE_NOT_VALID(400, Message.RESERVE_NOT_VALID),
    EMAIL_SENDING(500, Message.EMAIL_SENDING),
    EMAIL_ADDRESS_INVALID(400, Message.EMAIL_ADDRESS_INVALID),
    NOT_ENOUGH_BALANCE(400, Message.NOT_ENOUGH_BALANCE);

    private final int statusCode;
    private final String message;

    StatusCode(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public interface Message {
        String SUCCESS = "완료 되었습니다.";
        String UNAUTHORIZED = "인증에 실패하였습니다.";
        String NO_AUTH = "접근 권한이 없습니다.";
        String BAD_REQUEST = "잘못 입력하셨습니다.";
        String NOT_FOUND = "존재하지 않는 정보입니다.";
        String VALID_NOT_PHONE_NUM = "가입되지 않은 핸드폰 번호 입니다.";
        String VALID_NOT_PASSWORD = "잘못된 비밀번호 입니다.";
        String MEMBER_NOT_EXIST = "존재하지 않는 사용자 입니다.";
        String LOGIN_REQUIRED = "로그인이 필요합니다.";
        String PARAM_NOT_VALID = "파라미터 오류입니다.";
        String INTERNAL_ERROR = "시스템 오류가 발생하였습니다. 다시 시도해주세요.";
        String VALID_NOT_NULL = "이 필드는 비워둘 수 없습니다.";
        String VALID_NOT_REGEXP = "입력 형식이 올바르지 않습니다.";
        String UNSUPPORTED_ENCODING = "지원되지 않는 인코딩 형식입니다.";
        String NO_SUCH_ALGORITHM = "사용 가능한 암호화 알고리즘이 없습니다.";
        String URI_SYNTAX = "URI 형식이 잘못되었습니다.";
        String INVALID_KEY = "암호화 키가 유효하지 않습니다.";
        String JSON_PROCESSING = "JSON 처리 중 오류가 발생했습니다.";
        String ALREADY_EXISTS = "이미 존재하는 데이터입니다.";
        String RESERVE_NOT_VALID = "예약이 불가능한 공연입니다.";
        String EMAIL_SENDING = "이메일 전송 중 문제가 발생했습니다.";
        String EMAIL_ADDRESS_INVALID = "이메일 주소 형식이 잘못되었습니다.";
        String ALREADY_EXISTS_RESERVATION = "이미 예약된 좌석입니다.";
        String NOT_ENOUGH_BALANCE = "결재 가능한 금액을 초과했습니다.";
    }
}