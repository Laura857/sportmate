package com.example.sportmate.entity;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record Activity(
        @Id Integer id,

        boolean isEvent,

        String activityName,

        @NotNull(message = "Le date est obligatoire.")
        LocalDateTime activityDate,

        @NotNull(message = "Le créateur est obligatoire.")
        Integer creator,

        @NotBlank(message = "L'addresse est obligatoire'.")
        String address,

        @NotBlank(message = "La longitude est obligatoire.")
        String longitude,

        @NotBlank(message = "La latitude est obligatoire.")
        String latitude,

        Integer participant,

        @NotNull(message = "Le sport est obligatoire.")
        Integer sport,

        @NotNull(message = "Le niveau est obligatoire.")
        Integer activityLevel,

        String description,

        @NotBlank(message = "Le contact est obligatoire.")
        String contact,

        @NotNull(message = "La date de création est obligatoire.")
        LocalDate created,

        LocalDate updated) {
}
