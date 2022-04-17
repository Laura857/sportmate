package com.example.sportmate.mapper;

import com.example.sportmate.entity.Activity;
import com.example.sportmate.entity.Level;
import com.example.sportmate.entity.Sport;
import com.example.sportmate.entity.Users;
import com.example.sportmate.record.activity.ActivityRequestDto;
import com.example.sportmate.record.activity.ActivityResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ActivityMapper {
    public static Activity buildActivity(final ActivityRequestDto activityRequestDto, final Users user, final Sport sport, final Level level, final Integer activityId) {
        return new Activity(activityId, activityRequestDto.isEvent(), activityRequestDto.activityName(),
                activityRequestDto.activityDate(), user, activityRequestDto.address(),
                activityRequestDto.longitude(), activityRequestDto.latitude(), activityRequestDto.participant(),
                sport, level, activityRequestDto.description(), activityRequestDto.contact(), LocalDate.now(), null);
    }

    public static Activity buildActivity(final ActivityRequestDto activityRequestDto, final Users user, final Sport sport, final Level level) {
        return buildActivity(activityRequestDto, user, sport, level, null);
    }

    public static ActivityResponseDto buildActivityResponseDto(final Activity activity, final Sport sport, final Level activityLevel) {
        return new ActivityResponseDto(activity.getId(), activity.isEvent(), activity.getActivityName(),
                activity.getActivityDate(), activity.getCreator().getId(), activity.getAddress(),
                activity.getLongitude(), activity.getLatitude(), activity.getParticipant(),
                sport.getLabel(), activityLevel.getLabel(), activity.getContact(), activity.getDescription());
    }
}
