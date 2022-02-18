package com.example.sportmate.controller;

import com.example.sportmate.record.LoginRequestDto;
import com.example.sportmate.record.LoginResponseDto;
import com.example.sportmate.record.signin.SigningRequestDto;
import com.example.sportmate.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class LoginController {
    private final String SIGNING_AND_LOGIN = "api/signingAndLogin";
    private final String LOGIN =  "api/login";

    private final LoginService loginService;

    @Autowired
    public LoginController(final LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping(SIGNING_AND_LOGIN)
    private LoginResponseDto signingAndLogin(@RequestBody @Valid SigningRequestDto signingRequestDto){
        return loginService.signingAndLogin(signingRequestDto);
    }

    @PostMapping(LOGIN)
    private LoginResponseDto login(@RequestBody @Valid LoginRequestDto loginRequestDto){
        return loginService.login(loginRequestDto);
    }
}
