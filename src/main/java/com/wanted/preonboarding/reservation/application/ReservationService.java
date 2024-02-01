package com.wanted.preonboarding.reservation.application;

import com.wanted.preonboarding.core.exception.ReservationSoldOutException;
import com.wanted.preonboarding.reservation.application.output.EventOutputPort;
import com.wanted.preonboarding.reservation.domain.Reservation;
import com.wanted.preonboarding.reservation.domain.event.ReservationCancelEvent;
import com.wanted.preonboarding.reservation.domain.event.ReserveEvent;
import com.wanted.preonboarding.reservation.domain.vo.Item;
import com.wanted.preonboarding.reservation.domain.vo.NamePhone;
import com.wanted.preonboarding.reservation.domain.vo.ReserveItem;
import com.wanted.preonboarding.reservation.domain.vo.SeatInfo;
import com.wanted.preonboarding.reservation.framwork.jpaadapter.repository.ReservationRepository;
import com.wanted.preonboarding.reservation.framwork.presentation.dto.ReservationCancelRequest;
import com.wanted.preonboarding.reservation.framwork.presentation.dto.ReservationRequest;
import com.wanted.preonboarding.reservation.framwork.presentation.dto.ReservedItemResponse;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ReservationService {

	private final ReservationRepository reservationRepository;
	private final EventOutputPort eventOutputPort;

	@Transactional
	public ReservedItemResponse reserve(ReservationRequest request) {
		log.info("ReservationRequest ID => {}", request.performId());
		NamePhone namePhone = NamePhone.create(request.userName(), request.phoneNumber());
		SeatInfo seatInfo = SeatInfo.create(request.line(), request.seat());

		Reservation reservation = reservationRepository.findByNamePhone(namePhone)
			.orElse(Reservation.create(namePhone));

		ReserveEvent reserveEvent = Reservation.createReserveEvent(request.performId(), seatInfo);
		if(!eventOutputPort.reserveEventPublish(reserveEvent))
			throw new ReservationSoldOutException();

		// 1. 결제
		paymentGatewayStub(reservation);

		// 2. 예매 진행
		Item item = Item.create(request.performId(), request.name(), request.round(), seatInfo);
		UUID reserveNo = reservation.reserve(item, request.paymentAmount());
		reservationRepository.save(reservation);
		return ReservedItemResponse.of(reservation.getReserveItem(reserveNo),namePhone);
	}

	@Transactional
	public void cancel(ReservationCancelRequest request) {
		NamePhone namePhone = NamePhone.create(request.userName(), request.phoneNumber());
		Reservation reservation = reservationRepository.findByNamePhone(namePhone)
			.orElseThrow(EntityNotFoundException::new);

		//예약 취소
		int refundAmount = reservation.cancel(request.reservationId());

		//환불
		paymentGatewayStub(refundAmount);

		//이벤트 발생
		ReserveItem reserveItem = reservation.getReserveItem(request.reservationId());
		ReservationCancelEvent cancelEvent = Reservation.createCancelEvent(reserveItem);
		eventOutputPort.cancelEventPublish(cancelEvent);
	}

	public List<ReservedItemResponse> getReservations(String userName, String phoneNumber) {
		NamePhone namePhone = NamePhone.create(userName,phoneNumber);
		Reservation reservation = reservationRepository.findByNamePhone(namePhone)
			.orElseThrow(EntityNotFoundException::new);

		List<ReserveItem> reserveItemList = reservation.getReserveItemList();
		return reserveItemList.stream()
			.map(reserveItem -> ReservedItemResponse.of(reserveItem,namePhone))
			.collect(Collectors.toList());
	}


	private void paymentGatewayStub(int refundAmount) {
		log.info("refundAmount : {} 환불 성공", refundAmount);
	}


	private void paymentGatewayStub(Reservation reservation) {
		log.info("결제 성공");
	}

}
