package com.example.sportmate.record.activity;

import java.time.LocalDate;
import java.util.Comparator;

public record ActivityResponseDto(Integer id, boolean isEvent, String activityName, LocalDate activityDate,
                                  Integer creator, String address, String longitude, String latitude,
                                  Integer participant, String sport, String activityLevel, String contact, String description) {

    public static class DateComparator implements Comparator<ActivityResponseDto> {
        @Override
        public int compare(final ActivityResponseDto activity1, final ActivityResponseDto activity2) {
            return activity2.activityDate().compareTo(activity1.activityDate());
        }
    }
}
