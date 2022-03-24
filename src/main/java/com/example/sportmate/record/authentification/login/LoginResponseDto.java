package com.example.sportmate.record.authentification.login;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Objet de réponse pour la connexion")
public record LoginResponseDto(
        @Schema(example = "test@gmail.com")
        String email,

        @Schema(example = "unMotDePasseBienSolide")
        String token) {
}
