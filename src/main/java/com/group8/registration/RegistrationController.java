package com.group8.registration;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping( "/registration")
@AllArgsConstructor
public class RegistrationController {
    private RegistrationService registrationService;
    @GetMapping
    public String showRegistrationForm( Model model) {
        model.addAttribute("request", new RegistrationRequest());

        return "registration";
    }
    @PostMapping("/process")
    public String register( @ModelAttribute RegistrationRequest request){
        return registrationService.register("employee", request);
    }
}