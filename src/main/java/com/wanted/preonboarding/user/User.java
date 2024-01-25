package com.wanted.preonboarding.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    private PhoneNumber phoneNumber;

    @Builder
    private User(Long id, String name, PhoneNumber phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public static User createNewUser(String name, PhoneNumber phoneNumber) {
        return User.builder()
                .name(name)
                .phoneNumber(phoneNumber)
                .build();
    }
}
