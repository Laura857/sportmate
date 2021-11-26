package com.example.sportmate.service;

import com.example.sportmate.entity.Activity;
import com.example.sportmate.entity.Users;
import com.example.sportmate.exception.NotFoundException;
import com.example.sportmate.record.ActivityRequestDto;
import com.example.sportmate.record.ActivityResponseDto;
import com.example.sportmate.repository.ActivityRepository;
import com.example.sportmate.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static com.example.sportmate.mapper.ActivityMapper.buildActivity;
import static com.example.sportmate.mapper.ActivityMapper.buildActivityResponseDto;

public class ActivityService {
    private final ActivityRepository activityRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public ActivityService(final ActivityRepository activityRepository, final UsersRepository usersRepository) {
        this.activityRepository = activityRepository;
        this.usersRepository = usersRepository;
    }

    public void deleteActivity(Integer id){
        activityRepository.deleteById(id);
    }

    public void createActivity(ActivityRequestDto activityRequestDto, String token){
        Users user = usersRepository.findUsersByToken(token)
                .orElseThrow(() -> new NotFoundException("Utilisteur non trouvé"));
        Activity activityToSave = buildActivity(activityRequestDto, user.id());
        activityRepository.save(activityToSave);
    }

    public ActivityResponseDto getActivity(Integer id){
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Auncune activité trouvée avec l'id " + id));
        return buildActivityResponseDto(activity);
    }

    public List<ActivityResponseDto> getAllActivities(){
        List<ActivityResponseDto> activityResponse = new ArrayList<>();
        Iterable<Activity> allActivitiesFind = activityRepository.findAll();
        allActivitiesFind.forEach(activity -> activityResponse.add(buildActivityResponseDto(activity)));
        return activityResponse;
    }

    public List<ActivityResponseDto> getUserActivities(String token){
        List<ActivityResponseDto> activityResponse = new ArrayList<>();
        List<Activity> activitiesByToken = activityRepository.findActivitiesByToken(token);
        activitiesByToken.forEach(activity -> activityResponse.add(buildActivityResponseDto(activity)));
        return activityResponse;
    }

    public ActivityResponseDto updateActivity(ActivityRequestDto activityRequestDto, Integer id, String token){
        Users users = usersRepository.findUsersByToken(token)
                .orElseThrow(() -> new NotFoundException("Utilisteur non trouvé"));
        Activity activityFind = activityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Auncune activité trouvée avec l'id " + id));
        Activity activityToSave = buildActivity(activityRequestDto, users.id(), activityFind.id());
        Activity activitySaved = activityRepository.save(activityToSave);
        return buildActivityResponseDto(activitySaved);
    }
}
