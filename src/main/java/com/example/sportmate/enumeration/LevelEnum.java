package com.example.sportmate.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LevelEnum {
    BEGINNER("Débutant"),
    INTERMEDIATE("Intermédiaire"),
    EXPERT("Confirmé");

    private final String databaseValue;
}
