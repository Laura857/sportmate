package com.example.sportmate.record.authentification.signin;

import com.example.sportmate.record.authentification.login.LoginRequestDto;

import javax.validation.Valid;
import java.util.List;

public record SigningRequestDto(@Valid LoginRequestDto login,
                                @Valid UserRequestDto user,
                                @Valid List<SportRequestDto> sports,
                                List<String> hobbies) {
}

