package com.example.sportmate.mapper;

import com.example.sportmate.entity.Activity;
import com.example.sportmate.record.ActivityRequestDto;
import com.example.sportmate.record.ActivityResponseDto;

import java.time.LocalDate;

public class ActivityMapper {
    public static Activity buildActivity(ActivityRequestDto activityRequestDto, Integer creatorId, Integer activityId) {
        return new Activity(activityId, activityRequestDto.isEvent(), activityRequestDto.activityName(),
                activityRequestDto.activityDate(), creatorId, activityRequestDto.address(),
                activityRequestDto.longitude(), activityRequestDto.latitude(), activityRequestDto.participant(),
                activityRequestDto.sport(), activityRequestDto.activityLevel(), LocalDate.now(), null);
    }

    public static Activity buildActivity(ActivityRequestDto activityRequestDto, Integer creatorId) {
        return buildActivity(activityRequestDto, creatorId, null);
    }

    public static ActivityResponseDto buildActivityResponseDto(Activity activity) {
        return new ActivityResponseDto(activity.id(), activity.isEvent(), activity.activityName(),
                activity.activityDate(), activity.creator(), activity.address(),
                activity.longitude(), activity.latitude(), activity.participant(),
                activity.sport(), activity.activityLevel());
    }
}
