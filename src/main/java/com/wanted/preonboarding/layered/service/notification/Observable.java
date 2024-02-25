package com.wanted.preonboarding.layered.service.notification;

import com.wanted.preonboarding.layered.service.notification.message.Message;

public interface Observable<T> {
  boolean register(Observer<T> observer);
  boolean unregister(Observer<T> observer);
  boolean sendMessage(Message message);
}
