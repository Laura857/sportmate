package com.example.sportmate.record.activity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Objet de réponse avec les informations d'un participant")
public record ActivityParticipantsResponseDto(
        @Schema(example = "1")
        Integer id,

        @Schema(example = "Paul")
        String lastName,

        @Schema(example = "Dupont")
        String firstName) {
}
