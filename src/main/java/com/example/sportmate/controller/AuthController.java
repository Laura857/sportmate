package com.example.sportmate.controller;

import com.example.sportmate.record.authentification.login.LoginRequestDto;
import com.example.sportmate.record.authentification.login.LoginResponseDto;
import com.example.sportmate.record.authentification.signing.SigningRequestDto;
import com.example.sportmate.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class AuthController {
    private static final String SIGNING_AND_LOGIN = "api/signingAndLogin";
    private static final String LOGIN = "api/login";
    private static final String EMAIL_EXISTS = "api/email/{email}/exists";
    private static final String RESET_PASSWORD = "api/user/{email}/resetPassword";

    private final AuthService authService;

    @PostMapping(SIGNING_AND_LOGIN)
    @Operation(summary = "WS d'inscription puis de connexion")
    private LoginResponseDto signingAndLogin(@RequestBody @Valid final SigningRequestDto signingRequestDto) {
        return authService.signingAndLogin(signingRequestDto);
    }

    @PostMapping(LOGIN)
    @Operation(summary = "Ws de connexion")
    private LoginResponseDto login(@RequestBody @Valid final LoginRequestDto loginRequestDto) {
        return authService.login(loginRequestDto);
    }

    @GetMapping(EMAIL_EXISTS)
    @Operation(summary = "Ws de test d'existance d'un email")
    private boolean isEmailAlreadyUsedForAnotherAccount(@Schema(example = "test@gmail.com") @PathVariable("email") final String email) {
        return authService.isEmailAlreadyUsedForAnotherAccount(email);
    }

    @PutMapping(RESET_PASSWORD)
    @Operation(summary = "Ws de réinitialisation de mot de passe")
    private void resetPassword(@Schema(example = "test@gmail.com") @PathVariable("email") final String email) {
        authService.resetPassword(email);
    }
}
