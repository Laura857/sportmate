package com.example.sportmate.controller;

import com.example.sportmate.record.authentification.login.LoginRequestDto;
import com.example.sportmate.record.authentification.login.LoginResponseDto;
import com.example.sportmate.record.authentification.signing.SigningRequestDto;
import com.example.sportmate.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class LoginController {
    private static final String SIGNING_AND_LOGIN = "api/signingAndLogin";
    private static final String LOGIN = "api/login";

    private final LoginService loginService;

    @PostMapping(SIGNING_AND_LOGIN)
    @Operation(summary = "WS d'inscription puis de connexion")
    private LoginResponseDto signingAndLogin(@RequestBody @Valid final SigningRequestDto signingRequestDto) {
        return loginService.signingAndLogin(signingRequestDto);
    }

    @PostMapping(LOGIN)
    @Operation(summary = "Ws de connexion")
    private LoginResponseDto login(@RequestBody @Valid final LoginRequestDto loginRequestDto) {
        return loginService.login(loginRequestDto);
    }
}
