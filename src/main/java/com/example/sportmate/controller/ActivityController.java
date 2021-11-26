package com.example.sportmate.controller;

import com.example.sportmate.record.ActivityRequestDto;
import com.example.sportmate.record.ActivityResponseDto;
import com.example.sportmate.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ActivityController {
    private final String ACTIVITY = "api/activity";
    private final String ACTIVITY_ID = ACTIVITY + "/{id}";
    private final String ACTIVITY_ALL = ACTIVITY + "/all";
    private final String ACTIVITY_USER = ACTIVITY + "/user";
    private final String HEADER = "Authorization";

    private final ActivityService activityService;

    @Autowired
    public ActivityController(final ActivityService activityService) {
        this.activityService = activityService;
    }

    @DeleteMapping(ACTIVITY_ID)
    private void deleteActivity(@PathVariable Integer id){
        activityService.deleteActivity(id);
    }

    @PostMapping(ACTIVITY)
    private void createActivity(@RequestBody ActivityRequestDto activityRequestDto, @RequestHeader (name=HEADER) String token){
        activityService.createActivity(activityRequestDto, token);
    }

    @GetMapping(ACTIVITY_ID)
    private ActivityResponseDto getActivity(@PathVariable Integer id){
        return activityService.getActivity(id);
    }

    @GetMapping(ACTIVITY_ALL)
    private List<ActivityResponseDto> getAllActivities(){
        return activityService.getAllActivities();
    }

    @GetMapping(ACTIVITY_USER)
    private List<ActivityResponseDto> getUserActivities(@RequestHeader (name=HEADER) String token){
        return activityService.getUserActivities(token);
    }

    @PutMapping(ACTIVITY_ID)
    private ActivityResponseDto updateActivity(@RequestBody ActivityRequestDto activityRequestDto,
                                               @PathVariable Integer id,
                                               @RequestHeader (name=HEADER) String token){
        return activityService.updateActivity(activityRequestDto, id, token);
    }
}
