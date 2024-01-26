package com.wanted.preonboarding.user.presentation;

import com.wanted.preonboarding.user.application.UserInfoService;
import com.wanted.preonboarding.user.domain.dto.SignUpInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class UserController {

    private final UserInfoService userInfoService;

    @Autowired
    public UserController (UserInfoService userInfoService){
        this.userInfoService = userInfoService;
    }

    @GetMapping("/login")
    public String login(){
        return "userinfo/login";
    }



    @GetMapping("/signup")
    public String signup(){
        return "userinfo/signup";
    }

    @PostMapping("/signup")
    public String signup(SignUpInfo info){
        log.info("signup={}", info.getId());
        userInfoService.save(info);
        return "redirect: /";
    }
}
