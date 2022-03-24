package com.example.sportmate.controller;

import com.example.sportmate.record.authentification.login.LoginRequestDto;
import com.example.sportmate.record.authentification.login.LoginResponseDto;
import com.example.sportmate.record.authentification.signin.SigningRequestDto;
import com.example.sportmate.service.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class LoginController {
    private static final String SIGNING_AND_LOGIN = "api/signingAndLogin";
    private static final String LOGIN = "api/login";
    private static final String LOGOUT = "api/logout";

    private final LoginService loginService;

    @PostMapping(SIGNING_AND_LOGIN)
    private LoginResponseDto signingAndLogin(@RequestBody @Valid final SigningRequestDto signingRequestDto) {
        return loginService.signingAndLogin(signingRequestDto);
    }

    @PostMapping(LOGIN)
    private LoginResponseDto login(@RequestBody @Valid final LoginRequestDto loginRequestDto) {
        return loginService.login(loginRequestDto);
    }

    @GetMapping(LOGOUT)
    private void logout(final HttpServletRequest request) {
        loginService.logout(request);
    }
}
