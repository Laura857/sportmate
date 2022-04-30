package com.example.sportmate.controller;

import com.example.sportmate.record.ResponseDefaultDto;
import com.example.sportmate.record.activity.ActivityRequestDto;
import com.example.sportmate.record.activity.ActivityResponseDto;
import com.example.sportmate.service.ActivityService;
import com.example.sportmate.service.UserActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class ActivityController {
    private static final String ACTIVITY = "api/activity";
    private static final String ACTIVITY_ID = ACTIVITY + "/{id}";
    private static final String ACTIVITY_ALL = ACTIVITY + "/all";
    private static final String ACTIVITY_USER = ACTIVITY + "/user";
    private static final String ACTIVITY_USER_PARTICIPATE = ACTIVITY_USER + "/{userId}/participate";
    private static final String ACTIVITY_ID_USER_PARTICIPATE = ACTIVITY_ID + "/user/{userId}/participate";
    private static final String HEADER = "Authorization";
    private static final String ACTIVITY_SEARCH = ACTIVITY + "/search";
    private final ActivityService activityService;
    private final UserActivityService userActivityService;

    @DeleteMapping(ACTIVITY_ID)
    @Operation(summary = "WS qui supprime une activité")
    public ResponseEntity<ResponseDefaultDto> deleteActivity(@Schema(example = "1") @PathVariable final Integer id) {
        return activityService.deleteActivity(id);
    }

    @PostMapping(ACTIVITY)
    @Operation(summary = "WS qui créer une nouvelle activité")
    public ActivityResponseDto createActivity(@Valid @RequestBody final ActivityRequestDto activityRequestDto,
                                              @RequestHeader(name = HEADER) final String token) {
        return activityService.createActivity(activityRequestDto, token);
    }

    @GetMapping(ACTIVITY_ID)
    @Operation(summary = "WS qui récupère les informations d'une activité")
    public ActivityResponseDto getActivity(@Schema(example = "1") @PathVariable final Integer id) {
        return activityService.getActivity(id);
    }

    @GetMapping(ACTIVITY_ALL)
    @Operation(summary = "WS qui récupère les informations de toutes les activités")
    public List<ActivityResponseDto> getAllActivities() {
        return activityService.getAllActivities();
    }

    @GetMapping(ACTIVITY_USER)
    @Operation(summary = "WS qui récupère les informations des activités d'un utilisateur")
    public List<ActivityResponseDto> getUserActivities(@RequestHeader(name = HEADER) final String token) {
        return activityService.getUserActivities(token);
    }

    @PutMapping(ACTIVITY_ID)
    @Operation(summary = "WS qui récupère met à jour les informations d'une activité")
    public ActivityResponseDto updateActivity(@Valid @RequestBody final ActivityRequestDto activityRequestDto,
                                              @Schema(example = "1") @PathVariable final Integer id,
                                              @RequestHeader(name = HEADER) final String token) {
        return activityService.updateActivity(activityRequestDto, id, token);
    }

    @GetMapping(ACTIVITY_SEARCH)
    @Operation(summary = "WS de recherche par filtre pour les activités")
    public List<ActivityResponseDto> search(@RequestParam(value = "search") final String search) {
        return activityService.search(search);
    }

    @GetMapping(ACTIVITY_USER_PARTICIPATE)
    @Operation(summary = "WS qui récupère les informations des activités auxquelles un utilisateur participe")
    public List<ActivityResponseDto> getUserParticipateActivities(@Schema(example = "1") @PathVariable final Integer userId) {
        return userActivityService.getUserParticipateActivities(userId);
    }

    @PostMapping(ACTIVITY_ID_USER_PARTICIPATE)
    @Operation(summary = "WS qui permet à un utilisateur de participer à une activité")
    public void createUserParticipateActivity(@Schema(example = "1") @PathVariable("userId") final Integer userId,
                                              @Schema(example = "10") @PathVariable("id") final Integer activityId) {
        userActivityService.createUserParticipateActivity(userId, activityId);
    }

    @DeleteMapping(ACTIVITY_ID_USER_PARTICIPATE)
    @Operation(summary = "WS qui permet à un utilisateur de ne plus participer à une activité")
    public void deleteUserParticipateActivity(@Schema(example = "1") @PathVariable final Integer userId,
                                              @Schema(example = "10") @PathVariable("id") final Integer activityId) {
        userActivityService.deleteUserParticipateActivity(userId, activityId);
    }
}
