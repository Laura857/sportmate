package com.example.sportmate;

import com.example.sportmate.enumeration.GenreEnum;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static com.example.sportmate.record.Regex.LOCAL_DATE;
import static java.lang.Boolean.*;
import static java.util.Arrays.stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InstanceUtils {
    public static boolean isLocalDate(final String value) {
        return value.matches(LOCAL_DATE);
    }

    public static boolean isBoolean(final String value) {
        return TRUE.toString().equals(value) || FALSE.toString().equals(value);
    }

    public static Object instantiateObjectType(final String value) {
        if (isBoolean(value)) {
            return parseBoolean(value);
        }
        if (stream(GenreEnum.values()).anyMatch(genreEnum -> genreEnum.name().equals(value))) {
            return GenreEnum.valueOf(value);
        }
        if (isLocalDate(value)) {
            final String[] dateSplit = value.split("-");
            return LocalDate.of(Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]), Integer.parseInt(dateSplit[2]));
        }
        return value;
    }
}
