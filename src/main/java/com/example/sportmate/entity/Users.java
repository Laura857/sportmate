package com.example.sportmate.entity;

import com.example.sportmate.enumeration.GenreEnum;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public record Users(
        @Id
        @NotNull(message = "L'id est obligatoire.")
        Integer id,

        @NotBlank(message = "L'email est obligatoire.")
        String email,

        @NotBlank(message = "Le mot de passe est obligatoire.")
        String password,

        @NotBlank(message = "Le nom est obligatoire.")
        String lastName,

        @NotBlank(message = "Le prénom est obligatoire.")
        String firstName,

        @NotBlank(message = "Le téléphone est obligatoire.")
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
