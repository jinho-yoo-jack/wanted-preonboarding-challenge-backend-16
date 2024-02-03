package com.wanted.preonboarding.reservation.application;

import org.springframework.stereotype.Service;

import com.wanted.preonboarding.performance.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.reservation.domain.dto.ReserveInfo;

import lombok.RequiredArgsConstructor;

/**
 * 가격 정책 관련 서비스
 */
@Service
@RequiredArgsConstructor
public class DiscountService {
	
	/**
	 * 수령 가능 금액 & 공연장 금액 조건 조회  
	 * @param reserveInfo
	 * @param performanceInfo
	 * @return
	 */
	public boolean chkAmount(ReserveInfo reserveInfo,PerformanceInfo performanceInfo) {
		
		return (reserveInfo.getAmount()- discountPrice(reserveInfo, performanceInfo) >= 0);
	};
	
	/**
	 * 할인 정책 구현체
	 * @param reserveInfo
	 * @param performanceInfo
	 * @return
	 */
	private int discountPrice(ReserveInfo reserveInfo,PerformanceInfo performanceInfo) {
		/**
		 * 
		 *해당 위치에 할인 정책 서비스 구현 
		 * 
		 * */
		return performanceInfo.getPrice();
	}
}
