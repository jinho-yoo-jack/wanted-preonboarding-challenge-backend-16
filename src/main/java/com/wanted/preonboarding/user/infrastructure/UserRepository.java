package com.wanted.preonboarding.user.infrastructure;

import com.wanted.preonboarding.user.domain.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findById(String id);
    Optional<User> findByName(String name);
    User getReferenceByPhoneNumber(String phoneNumber);
}
