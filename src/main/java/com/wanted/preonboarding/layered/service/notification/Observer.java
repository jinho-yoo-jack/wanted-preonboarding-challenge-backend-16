package com.wanted.preonboarding.layered.service.notification;

import jakarta.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface Observer<T> {
  boolean update(T message) throws MessagingException, UnsupportedEncodingException;
}
