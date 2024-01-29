package com.wanted.preonboarding.user.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.preonboarding.user.domain.dto.SignUpInfo;
import com.wanted.preonboarding.user.domain.dto.UserInfo;
import com.wanted.preonboarding.user.domain.entity.User;
import com.wanted.preonboarding.user.infrastructure.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private ObjectMapper objectMapper;

    @Autowired
    public UserInfoService(UserRepository userInfoRepository, PasswordEncoder passwordEncoder, ObjectMapper objectMapper){
        this.userRepository = userInfoRepository;
        this.passwordEncoder = passwordEncoder;
        this.objectMapper = objectMapper;
    }

    public void save(SignUpInfo info){
        info.setPassword(passwordEncoder.encode(info.getPassword()));
        userRepository.save(User.of(info));

    }

    public UserInfo getUserInfo(String id) throws JsonProcessingException {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회원입니다."));
        return objectMapper.readValue(objectMapper.writeValueAsString(user), UserInfo.class);
    }



}
