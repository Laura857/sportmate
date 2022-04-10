package com.example.sportmate.record.hobbies;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Size;
import java.util.List;

@Schema(description = "Objet de requête pour la mise à jour des hobbies de l'utilisateur")
public record UpdateUserHobbiesDto(
        @Schema(description = "Objet de requête pour la mise à jour des hobbies de l'utilisateur", example = "['Cuisine', 'Cinéma']")
        @Size(min = 1, message = "Au moins un hobbies doit être renseigné.")
        List<String> hobbies) {
}

