package com.example.sportmate.record.user;

import com.example.sportmate.enumeration.GenreEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

import static com.example.sportmate.record.Regex.*;

public record UserDataDto(
        String profilePicture,

        boolean consents,

        @NotBlank(message = "L'email est obligatoire.")
        @Pattern(regexp = EMAIL, message = "L'email ne respecte pas le bon format")
        String email,

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

