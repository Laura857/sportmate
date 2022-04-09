package com.example.sportmate.record.authentification.signing;

import com.example.sportmate.record.authentification.login.LoginRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.Valid;
import java.util.List;

@Schema(description = "Objet de requête pour l'inscription")
public record SigningRequestDto(
        @Valid LoginRequestDto login,

        @Valid UserRequestDto user,

        @Valid List<SportDto> sports,

        @Schema(description = "Objet de requête pour l'inscription avec les hobbies l'utilisateur", example = "['Cuisine', 'Cinéma']")
        List<String> hobbies) {
}

