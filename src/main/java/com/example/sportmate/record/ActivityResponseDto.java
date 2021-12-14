package com.example.sportmate.record;

import java.time.LocalDate;
import java.util.Comparator;

public record ActivityResponseDto(Integer id, boolean isEvent, String activityName, LocalDate activityDate,
                                  Integer creator, String address, String longitude, String latitude,
                                  Integer participant, String sport, String activityLevel, String contact, String description) {

    public static class DateComparator implements Comparator<ActivityResponseDto> {
        @Override
        public int compare(ActivityResponseDto activity1, ActivityResponseDto activity2) {
            return activity1.activityDate().compareTo(activity2.activityDate());
        }
    }
}
