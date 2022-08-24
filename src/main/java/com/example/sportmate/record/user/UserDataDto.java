package com.example.sportmate.record.user;

import com.example.sportmate.enumeration.GenreEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

import static com.example.sportmate.record.Format.LOCAL_DATE;
import static com.example.sportmate.record.Regex.EMAIL;
import static com.example.sportmate.record.Regex.MOBILE_PHONE;


@Schema(description = "Objet avec les informations personnelles d'un utilisateur")
public record UserDataDto(
        @Schema(example = "https://uneImage")
        String profilePicture,

        @Schema(example = "false")
        boolean consents,

        @NotBlank(message = "L'email est obligatoire.")
        @Pattern(regexp = EMAIL, message = "L'email ne respecte pas le bon format.")
        @Schema(example = "test@gmail.com")
        String email,

        @NotBlank(message = "Le nom est obligatoire.")
        @Schema(example = "Paul")
        String lastName,

        @NotBlank(message = "Le prénom est obligatoire.")
        @Schema(example = "Dupont")
        String firstName,

        @NotNull(message = "Le genre est obligatoire.")
        GenreEnum genre,

        @NotNull(message = "La date d'anniversaire est obligatoire.")
        @Schema(example = "2021-10-10")
        LocalDate birthday,

        @NotBlank(message = "Le numéro de téléphone est obligatoire.")
        @Pattern(regexp = MOBILE_PHONE, message = "Le numéro de téléphone ne respecte pas le bon format.")
        @Schema(example = "0606060606")
        String mobilePhone) {
}

