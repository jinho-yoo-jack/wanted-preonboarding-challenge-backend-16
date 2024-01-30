package com.wanted.preonboarding.ticket.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {

    @GetMapping("/performance")
    public String performances(){
        return "performance/performanceList";
    }

    @GetMapping("/performance/{performanceId}")
    public String performanceDetail(@PathVariable String performanceId, Model model){
        model.addAttribute("performanceId", performanceId);
        return "performance/performanceDetail";
    }
}
