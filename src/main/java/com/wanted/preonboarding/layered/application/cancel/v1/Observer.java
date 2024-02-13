package com.wanted.preonboarding.layered.application.cancel.v1;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface Observer<T> {
    public boolean update(T message) throws MessagingException, UnsupportedEncodingException;
}
