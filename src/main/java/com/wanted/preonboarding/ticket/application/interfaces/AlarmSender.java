package com.wanted.preonboarding.ticket.application.interfaces;

import com.wanted.preonboarding.ticket.application.dto.MessageInfo;

public interface AlarmSender {

    void sendMessage(MessageInfo messageInfo);
}
