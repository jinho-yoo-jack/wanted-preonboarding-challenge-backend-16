package com.wanted.preonboarding.user.application;

import com.wanted.preonboarding.core.config.UserAuth;
import com.wanted.preonboarding.core.config.UserRole;
import com.wanted.preonboarding.user.domain.entity.User;
import com.wanted.preonboarding.user.infrastructure.UserRepository;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserSecurityService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public UserSecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("UserSecurityService.loadUserByUsername username={}", username);
        User user = userRepository.findById(username)
            .orElseThrow(() ->
                new UsernameNotFoundException(username));
        ArrayList<GrantedAuthority> auth = new ArrayList<>();
        auth.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        log.info("UserSecurityService.loadUserByUsername id={}, password={}, userRole={}", user.getId(), user.getPassword(), UserRole.USER.getValue());
        return new UserAuth(user.getId(), user.getPassword(), user.getName(), auth);
    }// username에 null 값이 들어오는 경우 html form의 파라미터 네임이 username이 맞는지 확인해야한다 아니라면 null 값이 들어옴
}
