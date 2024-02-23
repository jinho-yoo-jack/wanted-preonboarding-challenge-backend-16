package com.wanted.preonboarding.layered.service.ticket;

import com.wanted.preonboarding.domain.dto.reservation.NotificationDto;
import com.wanted.preonboarding.domain.entity.Notification;
import com.wanted.preonboarding.domain.entity.Reservation;
import com.wanted.preonboarding.domain.entity.SeatInfo;
import com.wanted.preonboarding.domain.entity.UserInfo;
import com.wanted.preonboarding.domain.exception.SeatNotFoundException;
import com.wanted.preonboarding.domain.exception.UserNotFoundException;
import com.wanted.preonboarding.layered.repository.NotificationRepository;
import com.wanted.preonboarding.layered.repository.SeatRepository;
import com.wanted.preonboarding.layered.repository.UserInfoRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class TicketOfficeV1 implements TicketOffice {
  private final NotificationRepository  notificationRepository;
  private final UserInfoRepository      userInfoRepository;
  private final SeatRepository          seatRepository;

  @Override
  @Transactional
  public Reservation sellTo(Client client, Ticket ticket) {
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

  @Override
  public List<NotificationDto> getSubscribers(UUID performanceId) {
    return this.notificationRepository
        .findAllByPerformanceId(performanceId)
        .stream().map(NotificationDto::of)
        .toList();
  }

  @Override
  public int setSubscribers(NotificationDto dto) {
    return this.notificationRepository.save(Notification.builder()
            .performanceId(dto.getPerformanceId())
            .user(this.userInfoRepository
                .findUserInfoByUserNameAndPhoneNumber(
                    dto.getUserName(),
                    dto.getPhoneNum()).orElseThrow(() -> new UserNotFoundException("사용자 조회 실패")))
            .mail(dto.getEmail())
        .build())
        .getId();
  }

  @Override
  public void delSubscriber(Integer id) {
    this.notificationRepository.deleteById(id);
  }
}
