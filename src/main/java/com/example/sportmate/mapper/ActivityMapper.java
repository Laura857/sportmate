package com.example.sportmate.mapper;

import com.example.sportmate.entity.Activity;
import com.example.sportmate.entity.Level;
import com.example.sportmate.entity.Sport;
import com.example.sportmate.entity.Users;
import com.example.sportmate.record.ActivityRequestDto;
import com.example.sportmate.record.ActivityResponseDto;

import java.time.LocalDate;

public class ActivityMapper {
    public static Activity buildActivity(ActivityRequestDto activityRequestDto, Users user, Sport sport, Level level, Integer activityId) {
        return new Activity(activityId, activityRequestDto.isEvent(), activityRequestDto.activityName(),
                activityRequestDto.activityDate(), user.id(), activityRequestDto.address(),
                activityRequestDto.longitude(), activityRequestDto.latitude(), activityRequestDto.participant(),
                sport.id(), level.id(), LocalDate.now(), null);
    }

    public static Activity buildActivity(ActivityRequestDto activityRequestDto, Users user, Sport sport, Level level) {
        return buildActivity(activityRequestDto, user, sport, level, null);
    }

    public static ActivityResponseDto buildActivityResponseDto(Activity activity) {
        return new ActivityResponseDto(activity.id(), activity.isEvent(), activity.activityName(),
                activity.activityDate(), activity.creator(), activity.address(),
                activity.longitude(), activity.latitude(), activity.participant(),
                activity.sport(), activity.activityLevel());
    }
}
