package com.wanted.preonboarding.ticket.dto.response.page;

import java.util.List;
import lombok.Builder;
import org.springframework.data.domain.Page;

@Builder
public record PageResponse<T>(
    List<T> contents,
    SortResponse sort,
    int currentPage,
    int pageSize,
    boolean first,
    boolean last,
    long totalElement,
    int totalPage

) {

    public static <T> PageResponse<T> of(Page<T> pageContents) {
        return PageResponse.<T>builder()
            .contents(pageContents.getContent())
            .sort(SortResponse.of(pageContents.getSort()))
            .currentPage(pageContents.getNumber() + 1)
            .pageSize(pageContents.getSize())
            .first(pageContents.isFirst())
            .last(pageContents.isLast())
            .totalElement(pageContents.getTotalElements())
            .totalPage(pageContents.getTotalPages())
            .build();
    }
}
