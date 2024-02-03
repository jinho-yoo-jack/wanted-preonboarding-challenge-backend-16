package com.wanted.preonboarding.ticket.presentation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wanted.preonboarding.base.rsData.RsData;
import com.wanted.preonboarding.ticket.application.PerformanceService;
import com.wanted.preonboarding.ticket.domain.entity.Performance;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/performance")
@RequiredArgsConstructor
public class PerformanceController {
	private final PerformanceService performanceService;

	/*
   		기능 3. 공연 및 전시 정보 조회(목록, 상세 조회)
 	*/
	@GetMapping
	public RsData showList(@RequestParam(name = "status", defaultValue = "enable") String status,
		@RequestParam(name = "page", defaultValue = "0") int page) {

		RsData reserveResult = performanceService.getList(status, page);

		if (reserveResult.isSuccess()) {
			reserveResult = transDTO(reserveResult);
		}

		return reserveResult;

	}

	private RsData transDTO(RsData reserveResult) {
		Page<Performance> list = (Page<Performance>)reserveResult.getData();
		List<ResponseShow> result = new ArrayList<>();
		for (Performance p : list) {
			result.add(ResponseShow.of(p));
		}
		reserveResult.setData(result);
		return reserveResult;
	}
	/*
		DTO 변환 메서드
	 */

	@Data
	@Builder
	public static class ResponseShow {
		private String name;
		private int round;
		private LocalDateTime startDate;
		private String status;

		public static ResponseShow of(Performance performance) {
			return ResponseShow.builder()
				.name(performance.getName())
				.round(performance.getRound())
				.startDate(performance.getStartDate())
				.status(performance.getIsReserve())
				.build();
		}
	}

}
