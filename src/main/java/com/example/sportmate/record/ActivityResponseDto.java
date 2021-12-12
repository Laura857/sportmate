package com.example.sportmate.record;

import java.time.LocalDate;

public record ActivityResponseDto(Integer id, boolean isEvent, String activityName, LocalDate activityDate,
                                  Integer creator, String address, String longitude, String latitude,
                                  Integer participant, Integer sport, Integer activityLevel, String contact, String description) {
}
