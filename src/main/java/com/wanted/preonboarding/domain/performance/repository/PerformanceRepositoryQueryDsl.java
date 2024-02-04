package com.wanted.preonboarding.domain.performance.repository;

import static com.wanted.preonboarding.domain.performance.domain.entity.QPerformance.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.preonboarding.domain.common.utils.QueryUtils;
import com.wanted.preonboarding.domain.performance.domain.entity.Performance;
import com.wanted.preonboarding.domain.performance.domain.enums.PerformanceStatus;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PerformanceRepositoryQueryDsl{

	private final JPAQueryFactory jpaQueryFactory;



	public Page<Performance> findPage(
		List<PerformanceStatus> statusList,
		int page,
		int size) {

		PageRequest pageRequest = PageRequest.of(page, size);

		QueryResults<Performance> results = this.jpaQueryFactory.select(performance)
			.from(performance)
			.where(
				QueryUtils.inEnumListIfNotNull(performance.performanceStatus, statusList))
			.offset(pageRequest.getOffset())
			.limit(pageRequest.getPageSize())
			.fetchResults(); // TODO

		return new PageImpl<>(results.getResults(), PageRequest.of(page, size), results.getTotal());
	}


}
