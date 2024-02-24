package com.wanted.preonboarding.layered.service.notification;

public interface Observable<T> {
  boolean register(Observer<T> observer);
  boolean unregister(Observer<T> observer);
  boolean sendMessage();
}
