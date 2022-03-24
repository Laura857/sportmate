package com.example.sportmate.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessageEnum {

    SPORT_NOT_FOUND("Sport non trouvé"),
    LEVEL_NOT_FOUND("Niveau non trouvé"),
    USER_NOT_FOUND("Utilisateur non trouvé");

    private final String message;
}
