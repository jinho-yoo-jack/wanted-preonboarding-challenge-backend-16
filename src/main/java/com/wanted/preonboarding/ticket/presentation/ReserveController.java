package com.wanted.preonboarding.ticket.presentation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wanted.preonboarding.base.rsData.RsData;
import com.wanted.preonboarding.ticket.application.ReservationService;
import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reserve")
@RequiredArgsConstructor
public class ReserveController {
	private final TicketSeller ticketSeller;
	private final ReservationService reservationService;

	/*
	   기능 1. 공연 예매
	 */
	@PostMapping("")
	public RsData reservation(@RequestBody ReserveInfo reserveInfo) {
		RsData reserveResult = ticketSeller.reserve(reserveInfo);
		if (reserveResult.isFail())
			return RsData.of(reserveResult.getResultCode(), reserveResult.getMsg());
		// 성공할때만 데이터 같이 넘기기
		return reserveResult;
	}

	/*
		기능 2. 예약 조회
	 */
	@Data
	public static class SearchRequest {
		@NotBlank(message = "예약자 이름은 필수 항목입니다.")
		private String name;
		@NotBlank(message = "예약자 휴대폰 번호는 필수 항목입니다.")
		private String phoneNumber;
	}

	@GetMapping("")
	public RsData search(@ModelAttribute @Valid SearchRequest searchRequest, BindingResult bindingResult) {
		// 요청 객체에서 입력하지 않은 부분이 있다면 메세지를 담아서 RsData 객체 바로 리턴
		if (bindingResult.hasErrors()) {
			List<String> errorMessages = bindingResult.getAllErrors()
				.stream()
				.map(error -> error.getDefaultMessage())
				.collect(Collectors.toList());
			return RsData.of("F-1", errorMessages.get(0));
		}
		// 입력 이상 없다면 조회
		RsData rsData = reservationService.search(searchRequest.getName(), searchRequest.getPhoneNumber());

		return rsData;
	}

	/*
		예약 티켓 취소
	 */
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class CancleRequest {
		@NotNull(message = "예약 번호는 필수 항목입니다.")
		private Long id;
		@NotBlank(message = "예약자 이름은 필수 항목입니다.")
		private String name;
		@NotBlank(message = "예약자 휴대폰 번호는 필수 항목입니다.")
		private String phoneNumber;
	}
	@DeleteMapping("")
	public RsData cancleTicket(@RequestBody @Valid CancleRequest cancleRequest, BindingResult bindingResult) {
		// 요청 객체에서 입력하지 않은 부분이 있다면 메세지를 담아서 RsData 객체 바로 리턴
		if (bindingResult.hasErrors()) {
			List<String> errorMessages = bindingResult.getAllErrors()
				.stream()
				.map(error -> error.getDefaultMessage())
				.collect(Collectors.toList());
			return RsData.of("F-1", errorMessages.get(0));
		}
		RsData reserveResult = reservationService.cancle(cancleRequest.getId(), cancleRequest.getName(), cancleRequest.getPhoneNumber());
		return reserveResult;
	}
}
