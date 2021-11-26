package com.example.sportmate.controller;

import org.springframework.web.bind.annotation.PostMapping;

public class LoginController {
    @PostMapping("/signup")
    String signup(){
        return "Spring is here";
    }

    @PostMapping("/login")
    String login(){
        return "Spring is here";
    }
}
