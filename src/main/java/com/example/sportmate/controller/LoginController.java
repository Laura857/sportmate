package com.example.sportmate.controller;

import com.example.sportmate.record.authentification.login.LoginRequestDto;
import com.example.sportmate.record.authentification.login.LoginResponseDto;
import com.example.sportmate.record.authentification.signing.SigningRequestDto;
import com.example.sportmate.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class LoginController {
    private static final String SIGNING_AND_LOGIN = "api/signingAndLogin";
    private static final String LOGIN = "api/login";
    private static final String EMAIL_EXISTS = "api/email/{email}/exists";

    private final LoginService loginService;

    @PostMapping(SIGNING_AND_LOGIN)
    @Operation(summary = "WS d'inscription puis de connexion")
    public LoginResponseDto signingAndLogin(@Valid @RequestBody final SigningRequestDto signingRequestDto) {
        return loginService.signingAndLogin(signingRequestDto);
    }

    @PostMapping(LOGIN)
    @Operation(summary = "Ws de connexion")
    public LoginResponseDto login(@Valid @RequestBody final LoginRequestDto loginRequestDto) {
        return loginService.login(loginRequestDto);
    }

    @GetMapping(EMAIL_EXISTS)
    @Operation(summary = "Ws de test d'existance d'un email")
    public boolean isEmailAlreadyUsedForAnotherAccount(@Schema(example = "test@gmail.com") @PathVariable("email") final String email) {
        return loginService.isEmailAlreadyUsedForAnotherAccount(email);
    }
}
