package com.wanted.preonboarding.layered.service.ticket;

import com.wanted.preonboarding.domain.dto.reservation.NotificationDto;
import com.wanted.preonboarding.domain.entity.Reservation;
import com.wanted.preonboarding.domain.entity.SeatInfo;
import com.wanted.preonboarding.domain.entity.UserInfo;
import com.wanted.preonboarding.domain.exception.PaymentException;
import com.wanted.preonboarding.domain.exception.SeatNotFoundException;
import com.wanted.preonboarding.domain.exception.UserNotFoundException;
import com.wanted.preonboarding.layered.repository.NotificationRepository;
import com.wanted.preonboarding.layered.repository.SeatRepository;
import com.wanted.preonboarding.layered.repository.UserInfoRepository;
import com.wanted.preonboarding.layered.service.ticket.discount.TicketOffice;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class TicketOfficeV1 implements TicketOffice {
  private final NotificationRepository  notificationRepository;
  private final UserInfoRepository      userInfoRepository;
  private final SeatRepository          seatRepository;

  public List<NotificationDto> getAllNotificationByPerformanceId(UUID uuid) {
    return this.notificationRepository.findAllByPerformanceId(uuid)
        .stream().map(
            NotificationDto::of
        ).toList();
  }

  @Transactional
  public Reservation sellTo(Client client, Ticket ticket) {
    if (client.pay(ticket.getTotalPrice()) == false)
      throw new PaymentException("잔액 부족");
    UserInfo user = this.userInfoRepository
        .findUserInfoByUserNameAndPhoneNumber(
            client.getName(),
            client.getPhoneNum())
        .orElseThrow(
            () -> new UserNotFoundException("유저 못찾음", HttpStatus.NOT_FOUND));
    SeatInfo seat = this.seatRepository
        .findSeatInfoByLineAndSeatAndPerformanceId(
            ticket.getLine(),
            ticket.getSeat(),
            ticket.getPerformanceId()
        ).orElseThrow(
            () -> new SeatNotFoundException("자리 못찾음", HttpStatus.NOT_FOUND));
    seat.setIsReserve("disable");
    return Reservation.builder()
        .userInfo(user)
        .seatInfo(seat)
        .build();
  }
}
