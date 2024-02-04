package com.wanted.preonboarding.domain.common.domain.dto;

import java.util.Collections;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PageDto<T> {

	private Long totalCount;
	private List<T> list;
	private boolean hasNext;

	public static <T> PageDto<T> of(
		List<T> list,
		Long totalCount,
		Boolean hasNext) {

		return new PageDto<>(totalCount, list, Boolean.TRUE.equals(hasNext));
	}

	public static <T> PageDto<T> of() {

		return new PageDto<>(0L, Collections.emptyList(), false);
	}
}
