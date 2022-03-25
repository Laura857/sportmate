package com.example.sportmate.entity;

import com.example.sportmate.enumeration.GenreEnum;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

import static com.example.sportmate.record.Regex.*;

public record Users(
        @Id
        @NotNull(message = "L'id est obligatoire.")
        Integer id,

        @NotBlank(message = "L'email est obligatoire.")
        @Pattern(regexp = EMAIL, message = "L'email ne respecte pas le bon format")
        String email,

        @NotBlank(message = "Le mot de passe est obligatoire.")
        @Pattern(regexp = PASSWORD, message = "Le mot de passe ne respecte pas le bon format")
        String password,

        @NotBlank(message = "Le nom est obligatoire.")
        String lastName,

        @NotBlank(message = "Le prénom est obligatoire.")
        String firstName,

        @NotBlank(message = "Le numéro de téléphone est obligatoire.")
        @Pattern(regexp = MOBILE_PHONE, message = "Le numéro de téléphone ne respecte pas le bon format")
        String mobile,

        String profilePicture,

        @NotNull(message = "Le genre est obligatoire.")
        GenreEnum genre,

        LocalDate birthday,

        boolean consents,

        @NotNull(message = "La date de création est obligatoire.")
        LocalDate created,

        LocalDate updated) {
}
