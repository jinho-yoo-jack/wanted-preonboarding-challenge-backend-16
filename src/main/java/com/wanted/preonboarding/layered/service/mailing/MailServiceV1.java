package com.wanted.preonboarding.layered.service.mailing;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceV1 implements MailService {
  private final JavaMailSender mailSender;

  @Override
  @Async
  public void sendMail(String mail, String title, String content)
      throws MessagingException, UnsupportedEncodingException {
    MimeMessage message = this.mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message);
    helper.setTo(mail);
    helper.setSubject(title);
    helper.setText(content, true);

    this.mailSender.send(message);
  }
}
