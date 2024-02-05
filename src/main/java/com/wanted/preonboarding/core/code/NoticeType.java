package com.wanted.preonboarding.core.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NoticeType {
    CANCEL_NOTICE("취소 시 잔여석 안내"),
    ;

    private final String description;
}
