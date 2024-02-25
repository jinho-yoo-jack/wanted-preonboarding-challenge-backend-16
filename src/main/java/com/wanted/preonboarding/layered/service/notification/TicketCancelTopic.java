package com.wanted.preonboarding.layered.service.notification;

import com.wanted.preonboarding.layered.service.notification.message.Message;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketCancelTopic implements Observable<Message> {
  private final Set<Observer<Message>> subscribers;

  @Override
  public boolean register(Observer<Message> observer) {
    return this.subscribers.add(observer);
  }

  @Override
  public boolean unregister(Observer<Message> observer) {
    return this.subscribers.remove(observer);
  }

  @Override
  public boolean sendMessage(Message message) {
    return this.subscribers.stream()
        .filter(observer -> observer.update(message))
        .count() == this.subscribers.size();
  }
}
