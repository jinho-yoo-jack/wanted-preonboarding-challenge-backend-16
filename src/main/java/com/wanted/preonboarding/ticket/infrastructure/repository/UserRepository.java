package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByNameAndPhoneNumber(String name, String phoneNumber);

    @Query(value = "SELECT u FROM Alarm a, User u WHERE a.userId = u.id and a.performanceId = :performanceId")
    List<User> findAlarmRegisteredUsers(@Param("performanceId") UUID performanceId);
}
