package com.wanted.preonboarding.layered.service.notification;

import com.wanted.preonboarding.domain.exception.TicketException;
import com.wanted.preonboarding.domain.exception.UserNotFoundException;
import com.wanted.preonboarding.domain.dto.reservation.NotificationDto;
import com.wanted.preonboarding.domain.dto.reservation.NotificationResponseDto;
import com.wanted.preonboarding.domain.entity.Notification;
import com.wanted.preonboarding.domain.entity.UserInfo;
import com.wanted.preonboarding.layered.repository.NotificationRepository;
import com.wanted.preonboarding.layered.repository.PerformanceRepository;
import com.wanted.preonboarding.layered.repository.UserInfoRepository;
import com.wanted.preonboarding.layered.service.mailing.MailService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceV1 implements NotificationService {
  final private UserInfoRepository      userInfoRepository;
  final private PerformanceRepository   performanceRepository;
  final private NotificationRepository  notificationRepository;
  final private MailService             mailService;

  public NotificationResponseDto register(NotificationDto dto) {
    UserInfo user = this.userInfoRepository.findUserInfoByUserNameAndPhoneNumber(
        dto.getUser().getUserName(),
        dto.getUser().getPhoneNumber()
    ).orElseThrow(UserNotFoundException::new);
    Notification notification = Notification.builder()
        .performanceId(dto.getPerformanceId())
        .userId(user.getId())
        .mail(dto.getMail())
        .build();
    this.notificationRepository.save(notification);
    return NotificationResponseDto.builder()
        .user(dto.getUser())
        .notificationId(notification.getId())
        .build();
  }
  public boolean unregister(long notificationId) {
    this.notificationRepository.deleteById(notificationId);
    return true;
  }
  //  TODO: Async
  public boolean notice(UUID performanceId) {
    List<Notification> target = this.notificationRepository.findAllByPerformanceId(performanceId);
    String performanceName = this.performanceRepository.findById(performanceId).orElseThrow(
        () -> new TicketException("공연 없음", HttpStatus.NOT_FOUND)).getName();
    SimpleMailMessage message = new SimpleMailMessage();
    message.setSubject("빈 자리 알림");
    message.setText(performanceName + "의 빈자리 알림");
    target.forEach(t -> this.mailService.sendMail(t.getMail(), message));
    return true;
  }
}
