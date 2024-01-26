package com.wanted.preonboarding.user.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.preonboarding.user.domain.dto.SignUpInfo;
import com.wanted.preonboarding.user.domain.entity.User;
import com.wanted.preonboarding.user.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserInfoService(UserRepository userInfoRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userInfoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(SignUpInfo info){
        info.setPassword(passwordEncoder.encode(info.getPassword()));
        userRepository.save(User.of(info));

    }



}
