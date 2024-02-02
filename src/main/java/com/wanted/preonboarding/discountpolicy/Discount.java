package com.wanted.preonboarding.discountpolicy;

import com.wanted.preonboarding.discountpolicy.impl.EarlyReserveDiscountPolycy;
import com.wanted.preonboarding.discountpolicy.impl.OldDiscountPolicy;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Discount {
    private int price;
    private List<DiscountPolicy> policyList = new ArrayList<>();

    public Discount (int price, Date birthday, LocalDateTime startDate){
        this.price = price;
        this.policyList.add(new OldDiscountPolicy(birthday));
        this.policyList.add(new EarlyReserveDiscountPolycy(startDate));

    }

    public int discountCalc(){

        for(int i = 0; i < policyList.size(); i++){
            policyList.get(i).setPrice(price);
            price = policyList.get(i).discount();
            System.out.println(price);
        }
        return price;
    }

}
