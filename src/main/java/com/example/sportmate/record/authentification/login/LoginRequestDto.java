package com.example.sportmate.record.authentification.login;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.example.sportmate.record.Regex.EMAIL;
import static com.example.sportmate.record.Regex.PASSWORD;

@Schema(description = "Objet de requête pour l'authentification'")
public record LoginRequestDto(
        @NotBlank(message = "L'email est obligatoire.")
        @Pattern(regexp = EMAIL, message = "L'email ne respecte pas le bon format.")
        @Schema(example = "test@gmail.com")
        String email,

        @NotBlank(message = "Le mot de passe est obligatoire.")
        @Pattern(regexp = PASSWORD, message = "Le mot de passe ne respecte pas le bon format.")
        @Schema(example = "unMot2P@sseBienSolide")
        String password) {
}
