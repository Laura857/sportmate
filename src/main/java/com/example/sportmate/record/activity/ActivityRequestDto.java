package com.example.sportmate.record.activity;

import java.time.LocalDate;

public record ActivityRequestDto(boolean isEvent, String activityName, LocalDate activityDate, String address,
                                 String longitude, String latitude, Integer participant, String sport,
                                 String activityLevel, String contact, String description) {
}
