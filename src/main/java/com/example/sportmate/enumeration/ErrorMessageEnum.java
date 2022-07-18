package com.example.sportmate.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessageEnum {
    SPORT_NOT_FOUND("Sport non trouvé"),
    HOBBIES_NOT_FOUND("Hobbies non trouvé"),
    LEVEL_NOT_FOUND("Niveau non trouvé"),
    USER_NOT_FOUND("Utilisateur non trouvé"),
    PASSWORD_BAD_REQUEST("Le mot de passe est incorrect");

    private final String message;
}
