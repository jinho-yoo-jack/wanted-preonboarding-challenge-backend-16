package com.wanted.preonboarding.discountpolicy.impl;

import com.wanted.preonboarding.discountpolicy.DiscountPolicy;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 만 나이로 65세 이상일 경우 경로 우대 할인 50% 적용
 */
public class OldDiscountPolicy implements DiscountPolicy {
    private int price;
    private LocalDate birthday;
    private LocalDate now;

    public OldDiscountPolicy (Date birthday){
        this.birthday = birthday.toLocalDate();
        this.now = LocalDate.now();
    }


    @Override
    public int discount() {
        int age = calcAge();
        if(age >= 65){
            price = (int) (price * 0.5);
            return price;
        }
        return price;
    }
    private int calcAge() {
        int currentYear = now.getYear();
        int currentMonth = now.getMonthValue();
        int currentDay = now.getDayOfMonth();
        int birthYear = birthday.getYear();
        int birthMonth = birthday.getMonthValue();
        int birthDay = birthday.getDayOfMonth();
        int age = currentYear - birthYear;

        if (birthMonth * 100 + birthDay > currentMonth * 100 + currentDay) {
            age -= 1;
        }
        return age;
    }

    @Override
    public void setPrice(int price) {
        this.price = price;
    }
}
