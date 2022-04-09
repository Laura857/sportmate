package com.example.sportmate.record.user;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.example.sportmate.record.Regex.PASSWORD;

@Schema(description = "Requête pour mettre à jour le mot de passe d'un utilisateur")
public record UpdatePasswordRequestDto(
        @NotBlank(message = "L'ancien mot de passe est obligatoire.")
        @Pattern(regexp = PASSWORD, message = "L'ancien mot de passe ne respecte pas le bon format.")
        @Schema(example = "unMot2P@sseBienSolide")
        String oldPassword,

        @NotBlank(message = "Le nouveau mot de passe est obligatoire.")
        @Pattern(regexp = PASSWORD, message = "Le nouveau mot de passe ne respecte pas le bon format.")
        @Schema(example = "unNouveauMot2P@sseBienSolide")
        String newPassword) {
}

