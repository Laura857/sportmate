package com.example.sportmate.controller;

import com.example.sportmate.record.LoginRequestDto;
import com.example.sportmate.record.LoginResponseDto;
import com.example.sportmate.record.SigninRequestDto;
import com.example.sportmate.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private final String SIGNIN = "api/signin";
    private final String LOGIN =  "api/login";

    private final LoginService loginService;

    @Autowired
    public LoginController(final LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping(SIGNIN)
    private void signin(@RequestBody SigninRequestDto signinRequestDto){
        loginService.signin(signinRequestDto);
    }

    @PostMapping(LOGIN)
    private LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto){
        return loginService.login(loginRequestDto);
    }
}
