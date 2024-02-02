package com.wanted.preonboarding.ticket.domain.dto.observer;

public interface Subject {
    public void register(Observer observer);
    public void unregister(Observer observer);
    public void notifyObservers(String message);
    public Object getUpdate(Observer observer);
}
