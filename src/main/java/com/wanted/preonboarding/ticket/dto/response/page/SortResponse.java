package com.wanted.preonboarding.ticket.dto.response.page;

import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

public record SortResponse(
    boolean sorted,
    Map<Direction, String> order
) {

    public static SortResponse of(Sort sort) {
        Map<Direction, String> order = sort.get()
            .collect(Collectors.toMap(Order::getDirection, Order::getProperty));
        return new SortResponse(sort.isSorted(), order);
    }

}
