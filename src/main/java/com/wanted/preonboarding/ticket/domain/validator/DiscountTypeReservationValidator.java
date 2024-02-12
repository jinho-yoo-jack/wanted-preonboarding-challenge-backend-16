package com.wanted.preonboarding.ticket.domain.validator;

import com.wanted.preonboarding.core.exception.DisableDiscountException;
import com.wanted.preonboarding.core.code.DiscountType;
import com.wanted.preonboarding.ticket.domain.entity.DiscountInfo;
import com.wanted.preonboarding.ticket.interfaces.dto.ReservationReadDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DiscountTypeReservationValidator implements ReservationValidator {
    @Override
    public int getOrder() {
        return 2;
    }

    @Override
    public void validate(ReservationReadDto reservationReadDto) {
        Set<DiscountType> requestDiscountTypes = reservationReadDto.getRequestDiscountTypes();
        List<DiscountInfo> discountInfos = reservationReadDto.getDiscountInfos();

        Set<DiscountType> activeDiscountTypes = discountInfos.stream().map(DiscountInfo::getType).collect(Collectors.toSet());
        if (!activeDiscountTypes.containsAll(requestDiscountTypes)) {
            throw new DisableDiscountException();
        }
    }
}
