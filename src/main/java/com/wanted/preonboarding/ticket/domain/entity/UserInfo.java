package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private String name;
    private String phoneNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserInfo userInfo = (UserInfo) o;
        return name.equals(userInfo.getName()) && phoneNumber.equals(userInfo.getPhoneNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phoneNumber);
    }
}
