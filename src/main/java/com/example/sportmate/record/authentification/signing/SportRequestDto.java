package com.example.sportmate.record.authentification.signing;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

@Schema(description = "Objet de requête pour l'inscription avec les sports favoris de l'utilisateur")
public record SportRequestDto(
        @NotBlank(message = "Le nom du sport est obligatoire.")
        @Schema(example = "Natation")
        String name,

        @NotBlank(message = "Le niveau du sport est obligatoire.")
        @Schema(example = "Débutant")
        String level) {
}

