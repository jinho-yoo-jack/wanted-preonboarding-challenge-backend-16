package com.wanted.preonboarding.ticket.application.validator;

import com.wanted.preonboarding.ticket.application.exception.AlreadyReservedStateException;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ReservationValidator {

    public static final String ALREADY_RESERVED_PERFORMANCE_MESSAGE_FORMAT =
            "[%s] 공연은 예약이 불가능 한 상태입니다.";
    public static final String ALREADY_RESERVED_SEAT_MESSAGE_FORMAT =
            "[%s] 가 이미 예약되어 있는 상태여서 예약이 불가능합니다.";

    public void validatePossibleReserve(Performance performance, PerformanceSeatInfo performanceSeatInfo) {
        if (!performance.isPossibleReserve()) {
            throw new AlreadyReservedStateException(
                    String.format(ALREADY_RESERVED_PERFORMANCE_MESSAGE_FORMAT, performance.getId())
            );
        }

        if (!performanceSeatInfo.isPossibleReserve()) {
            throw new AlreadyReservedStateException(
                    String.format(ALREADY_RESERVED_SEAT_MESSAGE_FORMAT, performanceSeatInfo.getId())
            );
        }
    }
}
