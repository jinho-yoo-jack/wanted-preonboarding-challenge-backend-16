package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.config.UserAuth;
import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.user.application.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

    private UserInfoService userInfoService;
    private TicketSeller ticketSeller;

    @Autowired
    public PageController (UserInfoService userInfoService, TicketSeller ticketSeller){
        this.userInfoService = userInfoService;
        this.ticketSeller = ticketSeller;
    }

    @GetMapping("/performance")
    public String performances(){
        return "performance/performanceList";
    }

    @GetMapping("/performance/{performanceId}")
    public String performanceDetail(@PathVariable String performanceId, Model model){
        model.addAttribute("performanceId", performanceId);
        return "performance/performanceDetail";
    }

    @GetMapping("/reservation/{performanceId}/{round}")
    public String reservationPage(@PathVariable String performanceId, @PathVariable int round, @RequestParam int seat, @RequestParam char line, @AuthenticationPrincipal
        UserAuth userAuth, Model model){
        model.addAttribute("userInfo", userInfoService.getUserAndPayments(userAuth.getUsername()));
        model.addAttribute("performanceInfo", ticketSeller.getPerformanceInfoDetailById(performanceId));
        model.addAttribute("performanceId", performanceId);
        model.addAttribute("performanceRound", round);
        model.addAttribute("seat", seat);
        model.addAttribute("line", line);


        return "reservation/reservationPage";
    }
}
