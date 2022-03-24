package com.example.sportmate.record.authentification.signin;

import com.example.sportmate.enumeration.GenreEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

import static com.example.sportmate.record.Regex.LOCAL_DATE;
import static com.example.sportmate.record.Regex.MOBILE_PHONE;

public record UserRequestDto(
        String profilePicture,

        boolean consents,

        @NotBlank(message = "Le nom est obligatoire.")
        String lastName,

        @NotBlank(message = "Le prénom est obligatoire.")
        String firstName,

        @NotNull(message = "Le genre est obligatoire.")
        GenreEnum genre,

        @NotNull(message = "La date d'anniversaire est obligatoire.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LOCAL_DATE)
        LocalDate birthday,

        @NotBlank(message = "Le numéro de téléphone est obligatoire.")
        @Pattern(regexp = MOBILE_PHONE, message = "Le numéro de téléphone ne respecte pas le bon format")
        String mobilePhone) {
}

