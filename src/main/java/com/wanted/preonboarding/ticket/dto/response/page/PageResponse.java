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
        return PageResponse.of(pageContents, pageContents.getContent());
    }

    public static <T> PageResponse<T> of(final Page<?> page, final List<T> contents) {
        return PageResponse.<T>builder()
            .contents(contents)
            .sort(SortResponse.of(page.getSort()))
            .currentPage(page.getNumber() + 1)
            .pageSize(page.getSize())
            .first(page.isFirst())
            .last(page.isLast())
            .totalElement(page.getTotalElements())
            .totalPage(page.getTotalPages())
            .build();
    }
}
