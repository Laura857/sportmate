package com.example.sportmate.service;

import com.example.sportmate.entity.Activity;
import com.example.sportmate.entity.Level;
import com.example.sportmate.entity.Sport;
import com.example.sportmate.entity.Users;
import com.example.sportmate.exception.AuthenticationException;
import com.example.sportmate.exception.NotFoundException;
import com.example.sportmate.record.ActivityRequestDto;
import com.example.sportmate.record.ActivityResponseDto;
import com.example.sportmate.record.ResponseDefaultDto;
import com.example.sportmate.repository.ActivityRepository;
import com.example.sportmate.repository.LevelRepository;
import com.example.sportmate.repository.SportRepository;
import com.example.sportmate.repository.UsersRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.sportmate.config.JWTAuthorizationFilter.PREFIX;
import static com.example.sportmate.config.JWTAuthorizationFilter.SECRET;
import static com.example.sportmate.mapper.ActivityMapper.buildActivity;
import static com.example.sportmate.mapper.ActivityMapper.buildActivityResponseDto;

@Service
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final UsersRepository usersRepository;
    private final SportRepository sportRepository;
    private final LevelRepository levelRepository;

    @Autowired
    public ActivityService(final ActivityRepository activityRepository,
                           final UsersRepository usersRepository,
                           final SportRepository sportRepository,
                           final LevelRepository levelRepository) {
        this.activityRepository = activityRepository;
        this.usersRepository = usersRepository;
        this.sportRepository = sportRepository;
        this.levelRepository = levelRepository;
    }

    public ResponseEntity<ResponseDefaultDto> deleteActivity(Integer id){
        activityRepository.deleteById(id);
        return new ResponseEntity<>(new ResponseDefaultDto("Activité " + id +" supprimé"), HttpStatus.OK);
    }

    public ActivityResponseDto createActivity(ActivityRequestDto activityRequestDto, String token){
        Sport sport = sportRepository.findByLabel(activityRequestDto.sport())
                .orElseThrow(()-> new NotFoundException("Sport non trouvé"));
        Level level = levelRepository.findByLabel(activityRequestDto.activityLevel())
                .orElseThrow(()-> new NotFoundException("Niveau non trouvé"));
        Users user = usersRepository.findByEmail(findEmailInToken(token))
                .orElseThrow(() -> new NotFoundException("Utilisteur non trouvé"));
        Activity activityToSave = buildActivity(activityRequestDto, user, sport, level);
        Activity activitySaved = activityRepository.save(activityToSave);
        return buildActivityResponseDto(activitySaved, sport, level);

    }

    public ActivityResponseDto getActivity(Integer id){
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Auncune activité trouvée avec l'id " + id));
        Sport sport = sportRepository.findById(activity.sport())
                .orElseThrow(()-> new NotFoundException("Sport non trouvé"));
        Level level = levelRepository.findById(activity.activityLevel())
                .orElseThrow(()-> new NotFoundException("Niveau non trouvé"));
        return buildActivityResponseDto(activity, sport, level);
    }

    public List<ActivityResponseDto> getAllActivities(){
        List<Activity> allActivitiesFind = (List<Activity>) activityRepository.findAll();
        return buildActivityResponseSortByDate(allActivitiesFind);
    }

    private List<ActivityResponseDto> buildActivityResponseSortByDate(List<Activity> allActivitiesFind) {
        List<ActivityResponseDto> activityResponse = new ArrayList<>();
        allActivitiesFind.forEach(activity -> {
            Sport sport = sportRepository.findById(activity.sport())
                    .orElseThrow(()-> new NotFoundException("Sport non trouvé"));
            Level level = levelRepository.findById(activity.activityLevel())
                    .orElseThrow(()-> new NotFoundException("Niveau non trouvé"));
            activityResponse.add(buildActivityResponseDto(activity, sport, level));
        });
        activityResponse.sort(new ActivityResponseDto.DateComparator());
        return activityResponse;
    }

    public List<ActivityResponseDto> getUserActivities(String token){
        List<Activity> activitiesByToken = activityRepository.findActivitiesByEmail(findEmailInToken(token));
        return buildActivityResponseSortByDate(activitiesByToken);
    }

    public ActivityResponseDto updateActivity(ActivityRequestDto activityRequestDto, Integer id, String token){
        Sport sport = sportRepository.findByLabel(activityRequestDto.sport())
                .orElseThrow(()-> new NotFoundException("Sport non trouvé"));
        Level level = levelRepository.findByLabel(activityRequestDto.activityLevel())
                .orElseThrow(()-> new NotFoundException("Niveau non trouvé"));
        Users user = usersRepository.findByEmail(findEmailInToken(token))
                .orElseThrow(() -> new NotFoundException("Utilisteur non trouvé"));
        Activity activityFind = activityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Auncune activité trouvée avec l'id " + id));

        Activity activityToSave = buildActivity(activityRequestDto, user, sport, level, activityFind.id());
        Activity activitySaved = activityRepository.save(activityToSave);
        return buildActivityResponseDto(activitySaved, sport, level);
    }

    private String findEmailInToken(String token){
        Claims claims = Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(token.replace(PREFIX, "")).getBody();
        if (claims.get("authorities") != null) {
            return claims.getSubject();
        }
        throw new AuthenticationException("Impossible de récupérer le mail dans le token");
    }
}
