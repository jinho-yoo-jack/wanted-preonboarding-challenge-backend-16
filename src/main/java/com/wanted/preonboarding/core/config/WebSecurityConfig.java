package com.wanted.preonboarding.core.config;

import com.wanted.preonboarding.user.application.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final UserSecurityService userSecurityService;

    @Autowired
    public WebSecurityConfig (UserSecurityService userSecurityService){
        this.userSecurityService = userSecurityService;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web -> web.ignoring()
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
        );  // static 경로에 대한 스프링 시큐리티 기능 비활성화
    }//"/static/**",

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests((authorize) -> authorize
            .requestMatchers("/", "/login", "/signup", "/performance", "/query/**").permitAll()    // 매개변수의 URL에 대한 엑세스 전부 허용으로 설정
            .anyRequest().authenticated()   // 그 외 요청 경로는 인증된 상태에서만 접근할 수 있도록 설정
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/")
                //.successForwardUrl("/")   -> Forward 요청에 대해서는 permitAll을 안해줘서 그랬던 것 같음
//                .failureUrl("/logian")
            )    // 폼 로그인 형식 로그인 설정
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
            );   // 로그아웃 설정
        // .httpBasic(HttpBasicConfigurer::disable) 일단 주석처리
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder,
        UserDetailsService userDetailsService) throws Exception{
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        ProviderManager providerManager = new ProviderManager(daoAuthenticationProvider);
        providerManager.setEraseCredentialsAfterAuthentication(false);
        return providerManager;
    }

    @Bean(name = "passwordEncoder")
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
