package com.wanted.preonboarding.user.application.mapper;

import com.wanted.preonboarding.user.User;
import com.wanted.preonboarding.user.infrastructure.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserReader {

    public static final String USER_NOT_FOUND_MESSAGE_FORMAT = "[%d] 유저를 찾을 수 없습니다.";

    private final UserRepository userRepository;

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND_MESSAGE_FORMAT, userId)));
    }
}
