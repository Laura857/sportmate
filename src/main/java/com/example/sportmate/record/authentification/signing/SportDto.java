package com.example.sportmate.record.authentification.signing;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

@Schema(description = "Contient les informations d'un sport")
public record SportDto(
        @NotBlank(message = "Le nom du sport est obligatoire.")
        @Schema(example = "Natation")
        String name,

        @NotBlank(message = "Le niveau du sport est obligatoire.")
        @Schema(example = "Débutant")
        String level) {
}

