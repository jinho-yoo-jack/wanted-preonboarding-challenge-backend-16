package com.wanted.preonboarding.user.infrastructure;

import com.wanted.preonboarding.user.User;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(Long userId);

    void deleteAllInBatch();
}
