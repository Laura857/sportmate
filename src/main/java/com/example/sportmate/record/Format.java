package com.example.sportmate.record;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Format {
    public static final String LOCAL_DATE = "^\\d{4}-\\d{2}-\\d{2}$";
}
