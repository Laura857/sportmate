package com.example.sportmate.record.activity;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;

@Schema(description = "Objet de réponse avec les informations d'une activité")
public record ActivityResponseDto(
        @Schema(example = "1")
        Integer id,

        @Schema(example = "false")
        boolean isEvent,

        @Schema(example = "Séance de natation")
        String activityName,

        @Schema(example = "2021-10-10")
        LocalDateTime activityDate,

        @Schema(example = "1")
        Integer creator,

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

    public static class DateComparator implements Comparator<ActivityResponseDto> {
        @Override
        public int compare(final ActivityResponseDto activity1, final ActivityResponseDto activity2) {
            return activity2.activityDate().compareTo(activity1.activityDate());
        }
    }
}
