package com.example.sportmate.record.signin;

import com.example.sportmate.record.LoginRequestDto;

import javax.validation.Valid;
import java.util.List;

public record SigningRequestDto(@Valid LoginRequestDto login,
                                @Valid UserRequestDto user,
                                @Valid List<SportRequestDto> sports,
                                List<String> hobbies) {
}

