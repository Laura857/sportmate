package com.example.sportmate.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PasswordService {
    private final PasswordEncoder passwordEncoder;

    public boolean isPasswordNoMatch(final String password, final String otherPassword) {
        return !passwordEncoder.matches(password, otherPassword);
    }
}
