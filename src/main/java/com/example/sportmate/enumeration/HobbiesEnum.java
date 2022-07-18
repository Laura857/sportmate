package com.example.sportmate.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HobbiesEnum {
    COOK("Cuisine"),
    MOVIE("Cinéma"),
    MUSIC("Musique");

    private final String databaseValue;
}
