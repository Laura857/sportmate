package com.example.sportmate.record;

import java.time.LocalDate;

public record ActivityRequestDto(boolean isEvent, String activityName, LocalDate activityDate, String address,
                                 String longitude, String latitude, Integer participant, String sport,
                                 String activityLevel) {
}
