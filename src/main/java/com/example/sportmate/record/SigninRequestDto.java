package com.example.sportmate.record;

import com.example.sportmate.enumeration.Sex;

import java.time.LocalDate;

public record SigninRequestDto(String email, String password, String lastName, String firstName,
                               String mobile, String profilePicture, Sex sex, LocalDate birthday, boolean consents) {
}

