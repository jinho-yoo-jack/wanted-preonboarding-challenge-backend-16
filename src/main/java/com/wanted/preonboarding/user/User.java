package com.wanted.preonboarding.user;

import com.wanted.preonboarding.core.domain.support.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Entity
public class User extends BaseEntity {

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

    public String getPhoneNumber() {
        return phoneNumber.getPhoneNumber();
    }

    public static User createNewUser(String name, PhoneNumber phoneNumber) {
        return User.builder()
                .name(name)
                .phoneNumber(phoneNumber)
                .build();
    }
}
