package com.wanted.preonboarding.layered.application.cancel.v1.sns;

import com.wanted.preonboarding.layered.application.cancel.v1.Observer;

public abstract class NotificationDecorator<T> implements Observer<T> {
    Observer<T> t;

    protected abstract void sendMessage(T t);

}
