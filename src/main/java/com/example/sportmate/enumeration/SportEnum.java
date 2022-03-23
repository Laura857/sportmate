package com.example.sportmate.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SportEnum {
    RUNNING("Course à pied"),
    SWIMMING("Natation"),
    BIKE("Vélo");

    private final String databaseValue;
}
