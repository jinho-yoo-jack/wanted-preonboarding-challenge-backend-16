package com.wanted.preonboarding.user.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wanted.preonboarding.core.config.UserAuth;
import com.wanted.preonboarding.user.application.UserInfoService;
import com.wanted.preonboarding.user.domain.dto.PaymentSetting;
import com.wanted.preonboarding.user.domain.dto.SignUpInfo;
import com.wanted.preonboarding.user.domain.dto.UserInfo;
import java.security.Principal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/user/{userUuid}")
    public String user(@PathVariable(name = "userUuid") String strUserUuid, Model model){
        log.info("userUuid={}", strUserUuid);

        UserInfo info = userInfoService.getUserInfo(strUserUuid);
        model.addAttribute("info", info);


        return "userinfo/userInfo";
    }   //custom principal 객체 사용 시 @AuthenticationPrincipal UserAuth userAuth와 같이 어노테이션 붙이기

    @GetMapping("/user/{userUuid}/paymentsetting")
    public String paymentSetting(@PathVariable String userUuid){
        log.info("paymentSetting: GET");
        return "payment/paymentSetting";
    }

    @PostMapping("/user/{userUuid}/paymentsetting")
    public String paymentSetting(PaymentSetting paymentSetting, @PathVariable String userUuid, RedirectAttributes redirectAttributes){
        log.info("paymentSetting: POST");
        userInfoService.setPaymentInfo(paymentSetting, userUuid);
        redirectAttributes.addAttribute("userUuid", userUuid);
        return "redirect:/user/{userUuid}";
    }
}
