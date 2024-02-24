package com.wanted.preonboarding.layered.service.mailing;

import jakarta.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface MailService {
  void  sendMail(String mail, String title, String content) throws MessagingException, UnsupportedEncodingException;
}
