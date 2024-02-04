package com.wanted.preonboarding.ticket.application.discount;

import com.wanted.preonboarding.ticket.domain.exception.exceptions.DiscountTypeNotMatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class DiscountServiceFactory {
    private final List<DiscountService> discountServices;

    public DiscountService getTypeOf(String discountType) {
        return discountServices.stream()
                .filter((ds) -> ds.getDiscountType().equalsIgnoreCase(discountType))
                .findFirst()
                .orElseThrow(DiscountTypeNotMatchException::new);
    }

}
