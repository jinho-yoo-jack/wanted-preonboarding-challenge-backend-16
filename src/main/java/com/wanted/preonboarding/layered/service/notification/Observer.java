package com.wanted.preonboarding.layered.service.notification;

public interface Observer<T> {
  boolean update(T message);
}
