package com.example.sportmate.record.activity;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Objet de requête avec les informations d'une activité")
public record ActivityRequestDto(
        @Schema(example = "false")
        boolean isEvent,

        @Schema(example = "Séance de natation")
        String activityName,

        @Schema(example = "2021-10-10")
        LocalDate activityDate,

        @Schema(example = "16 rue de la Poutre, 75003 Paris")
        String address,

        @Schema(example = "123.023.023")
        String longitude,

        @Schema(example = "123.023.023")
        String latitude,

        @Schema(example = "3")
        Integer participant,

        @Schema(example = "Natation")
        String sport,

        @Schema(example = "Débutant")
        String activityLevel,

        @Schema(example = "0606060606")
        String contact,

        @Schema(example = "Une petite séance de natation pour être en pleine forme !")
        String description) {
}
