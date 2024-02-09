package com.wanted.preonboarding.discountpolicy.impl;

import com.wanted.preonboarding.discountpolicy.DiscountPolicy;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

/**
 * 공연 시작 30일 전까지 예약 시 조기 예약 할인 10% 적용
 */
public class EarlyReserveDiscountPolycy implements DiscountPolicy {
    private int price;
    private LocalDate startDate;
    private LocalDate now;

    public EarlyReserveDiscountPolycy(LocalDateTime startDate){
        this.startDate = startDate.toLocalDate();
        this.now = LocalDate.now();
    }
    @Override
    public int discount() {
        return calcDate()? (int) (price * 0.9) : price;
    }

    private boolean calcDate(){
        int currentYear = now.getYear();
        int currentMonth = now.getMonthValue();
        int startYear = startDate.getYear();
        int startMonth = startDate.getMonthValue();


        if(startYear - currentYear >= 1){
            return true;
        }
        if(startMonth - currentMonth >= 1){
            return true;
        }
        Period diffDay = Period.between(startDate, now);
        return diffDay.getDays() >= 30;
    }

    @Override
    public void setPrice(int price) {
        this.price = price;
    }
}
