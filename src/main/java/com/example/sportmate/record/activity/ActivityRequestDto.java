package com.example.sportmate.record.activity;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

import static com.example.sportmate.record.Regex.MOBILE_PHONE;

@Schema(description = "Objet de requête avec les informations d'une activité")
public record ActivityRequestDto(
        @Schema(example = "false")
        boolean isEvent,

        @Schema(example = "Séance de natation")
        String activityName,

        @NotNull(message = "Le date est obligatoire.")
        @Schema(example = "2021-10-10")
        LocalDateTime activityDate,

        @NotBlank(message = "L'addresse est obligatoire'.")
        @Schema(example = "16 rue de la Poutre, 75003 Paris")
        String address,

        @Schema(example = "123.023.023", required = true)
        @NotBlank(message = "La longitude est obligatoire.")
        String longitude,

        @Schema(example = "123.023.023", required = true)
        @NotBlank(message = "La latitude est obligatoire.")
        String latitude,

        @Schema(example = "3")
        Integer participant,

        @NotNull(message = "Le sport est obligatoire.")
        @Schema(example = "Natation")
        String sport,

        @NotNull(message = "Le niveau est obligatoire.")
        @Schema(example = "Débutant")
        String activityLevel,

        @NotBlank(message = "Le contact est obligatoire.")
        @Pattern(regexp = MOBILE_PHONE, message = "Le numéro de téléphone du contact ne respecte pas le bon format.")
        @Schema(example = "0606060606")
        String contact,

        @Schema(example = "Une petite séance de natation pour être en pleine forme !")
        String description) {
}
