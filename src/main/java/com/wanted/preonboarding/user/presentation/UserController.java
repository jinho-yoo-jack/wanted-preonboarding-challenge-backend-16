package com.wanted.preonboarding.user.presentation;

import com.wanted.preonboarding.core.config.UserAuth;
import com.wanted.preonboarding.user.application.UserInfoService;
import com.wanted.preonboarding.user.domain.dto.SignUpInfo;
import java.security.Principal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        log.info("loginPage");
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
        return "redirect:/";
    }

    @ResponseBody
    @GetMapping("/user")
    public String user(@AuthenticationPrincipal UserAuth userAuth){
        log.info("UserAuth.getUserName={}", userAuth.getUsername());
        return "테스트";
    }   //custom principal 객체 사용 시 @AuthenticationPrincipal UserAuth userAuth와 같이 어노테이션 붙이기
}
