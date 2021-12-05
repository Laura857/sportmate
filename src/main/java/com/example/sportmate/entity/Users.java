package com.example.sportmate.entity;

import com.example.sportmate.enumeration.Sex;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

public record Users(@Id Integer id, String email, String password, String lastName,
                    String firstName, String mobile, String profilePicture, Sex sex, LocalDate birthday,
                    boolean consents, LocalDate created, LocalDate updated) {
}
