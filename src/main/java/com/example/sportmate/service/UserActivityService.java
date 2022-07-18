package com.example.sportmate.service;

import com.example.sportmate.entity.Activity;
import com.example.sportmate.record.activity.ActivityResponseDto;
import com.example.sportmate.repository.UserActivityRepository;
import com.example.sportmate.repository.activity.ActivityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UserActivityService {

    private final UserActivityRepository userActivityRepository;
    private final ActivityRepository activityRepository;
    private final ActivityService activityService;

    public List<ActivityResponseDto> getUserParticipateActivities(final Integer userId) {
        final List<Activity> activities = activityRepository.findParticipateActivitiesByUserId(userId);
        return activityService.buildActivityResponseSortByDate(activities);
    }

    @Transactional
    public void createUserParticipateActivity(final Integer userId, final Integer activityId) {
        userActivityRepository.save(userId, activityId);
    }

    @Transactional
    public void deleteUserParticipateActivity(final Integer userId, final Integer activityId) {
        userActivityRepository.deleteByUserIdAndActivityId(userId, activityId);
    }
}
