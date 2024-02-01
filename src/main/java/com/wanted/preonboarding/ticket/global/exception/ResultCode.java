package com.wanted.preonboarding.ticket.global.exception;

public enum ResultCode {
    SUCCESS(200, ResultMessage.SUCCESS),
    UNAUTHORIZED(401, ResultMessage.UNAUTHORIZED),
    NO_AUTH(403, ResultMessage.NO_AUTH),
    BAD_REQUEST(404, ResultMessage.BAD_REQUEST),
    NOT_FOUND(405, ResultMessage.NOT_FOUND),
    INTERNAL_ERROR(500, ResultMessage.INTERNAL_ERROR),
    VALID_NOT_PHONE_NUM(1_007, ResultMessage.VALID_NOT_PHONE_NUM),
    VALID_NOT_PASSWORD(1_008, ResultMessage.VALID_NOT_PASSWORD),
    MEMBER_NOT_EXIST(1_012, ResultMessage.MEMBER_NOT_EXIST),
    LOGIN_REQUIRED(1_019, ResultMessage.LOGIN_REQUIRED),
    PARAM_NOT_VALID(2_000, ResultMessage.PARAM_NOT_VALID),
    VALID_NOT_NULL(400, ResultMessage.VALID_NOT_NULL),
    VALID_NOT_REGEXP(400, ResultMessage.VALID_NOT_REGEXP),
    UNSUPPORTED_ENCODING(400, ResultMessage.UNSUPPORTED_ENCODING),
    NO_SUCH_ALGORITHM(500, ResultMessage.NO_SUCH_ALGORITHM),
    URI_SYNTAX(400, ResultMessage.URI_SYNTAX),
    INVALID_KEY(500, ResultMessage.INVALID_KEY),
    JSON_PROCESSING(400, ResultMessage.JSON_PROCESSING)
    ;

    private final int resultCode;
    private final String resultMessage;

    ResultCode(int resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public interface ResultMessage {
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
    }
}