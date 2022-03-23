package com.example.sportmate.record;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.example.sportmate.record.Regex.EMAIL;


public record LoginRequestDto(
        @NotBlank(message = "L'email est obligatoire.")
        @Pattern(regexp = EMAIL, message = "L'email ne respecte pas le bon format")
        String email,

        @NotBlank(message = "Le mot de passe est obligatoire.")
        String password) {
}
