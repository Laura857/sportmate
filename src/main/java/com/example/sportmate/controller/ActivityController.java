package com.example.sportmate.controller;

import com.example.sportmate.record.ActivityRequestDto;
import com.example.sportmate.record.ActivityResponseDto;
import com.example.sportmate.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class ActivityController {
    private final String ACTIVITY = "/activity";
    private final String ACTIVITY_ID = ACTIVITY + "/id";
    private final String ACTIVITY_ALL = ACTIVITY + "/all";
    private final String ACTIVITY_USER = ACTIVITY + "/user";

    private final ActivityService activityService;

    @Autowired
    public ActivityController(final ActivityService activityService) {
        this.activityService = activityService;
    }

    @DeleteMapping(ACTIVITY_ID)
    private void deleteActivity(Integer id){
        activityService.deleteActivity(id);
    }

    @PostMapping(ACTIVITY)
    private void createActivity(ActivityRequestDto activityRequestDto, String token){// TODO mettre dans le header
        activityService.createActivity(activityRequestDto, token);
    }

    @GetMapping(ACTIVITY_ID)
    private ActivityResponseDto getActivity(Integer id){
        return activityService.getActivity(id);
    }

    @GetMapping(ACTIVITY_ALL)
    private List<ActivityResponseDto> getAllActivities(){
        return activityService.getAllActivities();
    }

    @GetMapping(ACTIVITY_USER)
    private List<ActivityResponseDto> getUserActivities(String token){// TODO mettre dans le header
        return activityService.getUserActivities(token);
    }

    @PutMapping(ACTIVITY_ID)
    private ActivityResponseDto updateActivity(ActivityRequestDto activityRequestDto, Integer id, String token){// TODO mettre dans le header
        return activityService.updateActivity(activityRequestDto, id, token);
    }
}
