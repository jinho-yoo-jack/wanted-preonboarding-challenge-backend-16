package com.wanted.preonboarding.layered.application.cancel.v1;

public interface Observable<T> {
    public boolean register(Observer<T> o);
    public boolean remove(Observer<T> o);
    public boolean sendMessage();
}
