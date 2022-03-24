package com.example.sportmate.record.authentification.login;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.example.sportmate.record.Regex.EMAIL;

@Schema(description = "Objet de requête pour l'authentification'")
public record LoginRequestDto(
        @NotBlank(message = "L'email est obligatoire.")
        @Pattern(regexp = EMAIL, message = "L'email ne respecte pas le bon format")
        @Schema(example = "test@gmail.com")
        String email,

        @NotBlank(message = "Le mot de passe est obligatoire.")
        @Schema(example = "unMotDePassBienSolide")
        String password) {
}
