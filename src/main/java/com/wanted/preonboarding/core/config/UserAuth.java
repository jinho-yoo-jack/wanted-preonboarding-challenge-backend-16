package com.wanted.preonboarding.core.config;

import com.wanted.preonboarding.user.domain.entity.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
public class UserAuth implements UserDetails {

    private UUID userUuid;
    private String id;
    private String password;
    private String name;

    private Collection<? extends GrantedAuthority> auth;

    public UserAuth (UUID userUuid, String id, String password, String name, List<GrantedAuthority> auth){
        this.userUuid = userUuid;
        this.id = id;
        this.password = password;
        this.name = name;
        this.auth = auth;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return auth;
    }

    @Override
    public String getPassword() {
        log.info("UserAuth.password={}", password);
        return password;
    }

    @Override
    public String getUsername() {
        log.info("UserAuth.id={}", id);
        return id;
    }

    public UUID getUserUuid(){
        log.info("UserAuth.userUuid={}", userUuid);
        return userUuid;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
