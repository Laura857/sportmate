package com.example.sportmate.record.authentification.signing;

import com.example.sportmate.enumeration.GenreEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

import static com.example.sportmate.record.Regex.MOBILE_PHONE;

@Schema(description = "Objet de requête pour l'inscription avec les données de l'utilisateur")
public record UserRequestDto(
        @Schema(example = "https://uneImage")
        String profilePicture,

        @Schema(example = "false")
        boolean consents,

        @NotBlank(message = "Le nom est obligatoire.")
        @Schema(example = "Paul")
        String lastName,

        @NotBlank(message = "Le prénom est obligatoire.")
        @Schema(example = "Dupont")
        String firstName,

        @NotNull(message = "Le genre est obligatoire.")
        GenreEnum genre,

        @Schema(example = "2021-10-10")
        LocalDate birthday,

        @NotBlank(message = "Le numéro de téléphone est obligatoire.")
        @Pattern(regexp = MOBILE_PHONE, message = "Le numéro de téléphone ne respecte pas le bon format.")
        @Schema(example = "0606060606")
        String mobilePhone) {
}

