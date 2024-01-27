package com.wanted.preonboarding.user.support;

import com.wanted.preonboarding.user.PhoneNumber;
import com.wanted.preonboarding.user.User;

public final class UserFactory {

    private UserFactory() {

    }

    public static User create() {
        return User.createNewUser("원티드", PhoneNumber.builder()
                .phoneNumber("010-1111-2222")
                .build()
        );
    }
}
