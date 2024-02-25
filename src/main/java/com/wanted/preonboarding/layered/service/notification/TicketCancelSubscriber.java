package com.wanted.preonboarding.layered.service.notification;

import com.wanted.preonboarding.domain.dto.reservation.NotificationDto;
import com.wanted.preonboarding.layered.repository.NotificationRepository;
import com.wanted.preonboarding.layered.service.mailing.MailService;
import com.wanted.preonboarding.layered.service.notification.message.Message;
import jakarta.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TicketCancelSubscriber implements Observer<Message> {
  private final NotificationRepository notiRepo;
  private final MailService   mailService;

  @Override
  public boolean update(Message message) {
    this.notiRepo.findAllByPerformanceId(message.getPerformanceId()).stream()
        .map(NotificationDto::of).forEach(
          user -> {
            try {
              this.mailService.sendMail(user.getEmail(),
                  "티켓 취소 알림",
                  "<p>" + message.getPerformanceName() + "</p>" +
                      "<p>" + message.getRound() + " 회차" + "</p>" +
                      "<p>취소 알림</p>"
              );
            } catch (MessagingException | UnsupportedEncodingException e) {
              log.info("메일 전송 실패");
            }
          });
    return true;
  }
}
