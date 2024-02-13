package com.wanted.preonboarding.layered.domain.dto;

import lombok.Getter;

@Getter
public class YourAction {
    private String name;
    private int action; // 0: write annotation Getter 1: write annotation Setter

    public void setDoItAction(int i){
        if(i == 0 || i == 1){
            action = i;
        }
    }

    public static void main(String[] args) {
        YourAction a = new YourAction();
        // 간접적으로 action 필드의 존재 여부
        // 저자(YourAction의 창조주, A개발자)의 의도와 다른 값을 할당
    }
}
