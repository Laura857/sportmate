package com.example.sportmate.record;

import java.time.LocalDate;

public record ActivityResponseDto(Integer id, boolean isEvent, String activityName, LocalDate activityDate,
                                  Integer creator, String address, String longitude, String latitude,
                                  Integer participant, String sport, String activityLevel, String contact, String description) {
}
