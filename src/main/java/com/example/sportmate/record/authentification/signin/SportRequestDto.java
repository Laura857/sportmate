package com.example.sportmate.record.authentification.signin;

import javax.validation.constraints.NotBlank;

public record SportRequestDto(
        @NotBlank(message = "Le nom du sport est obligatoire.")
        String name,

        @NotBlank(message = "Le niveau du sport est obligatoire.")
        String level) {
}

