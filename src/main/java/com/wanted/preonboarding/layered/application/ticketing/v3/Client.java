package com.wanted.preonboarding.layered.application.ticketing.v3;

public class Client {
    private final String name;
    private final String phoneNumber;
    private int depositAmount;

    public Client(String userName, String userPhoneNumber, int userDepositAmount) {
        name = userName;
        phoneNumber = userPhoneNumber;
        depositAmount = userDepositAmount;
    }

    public boolean pay(int ticketFee) {
        if (ticketFee < depositAmount) {
            depositAmount -= ticketFee;
            return true;
        }
        return false;
    }

}
