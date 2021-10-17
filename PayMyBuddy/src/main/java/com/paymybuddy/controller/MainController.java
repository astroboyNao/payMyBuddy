package com.paymybuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/home")
    public String home() {
        return "/home";
    }
    
    @GetMapping("/transfer")
    public String transfer() {
        return "/transfer";
    }
    
    @GetMapping("/profil")
    public String profil() {
        return "/profil";
    }
    
    @GetMapping("/contact")
    public String contact() {
        return "/contact";
    }
    
    @GetMapping("/login")
    public String login() {
        return "/login";
    }
    
    @GetMapping("/logout")
    public String logout() {
        return "/logout";
    }
}
