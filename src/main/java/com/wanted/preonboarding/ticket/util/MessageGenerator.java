package com.wanted.preonboarding.ticket.util;

import com.wanted.preonboarding.ticket.domain.entity.Reservation;

public class MessageGenerator {
    public static String performanceCancelMessage(Reservation reservation) {
        int round = reservation.getRound();
        int gate = reservation.getGate();
        char line = reservation.getLine();
        int seat = reservation.getSeat();

        return "회차: " + round + "\n" +
                "게이트: " + gate + "\n" +
                "라인: " + line + "\n" +
                "좌석: " + seat + "\n";
    }
}
