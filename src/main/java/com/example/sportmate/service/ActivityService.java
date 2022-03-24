package com.example.sportmate.service;

import com.example.sportmate.entity.Activity;
import com.example.sportmate.entity.Level;
import com.example.sportmate.entity.Sport;
import com.example.sportmate.entity.Users;
import com.example.sportmate.exception.AuthenticationException;
import com.example.sportmate.exception.NotFoundException;
import com.example.sportmate.record.activity.ActivityRequestDto;
import com.example.sportmate.record.activity.ActivityResponseDto;
import com.example.sportmate.record.ResponseDefaultDto;
import com.example.sportmate.repository.ActivityRepository;
import com.example.sportmate.repository.LevelRepository;
import com.example.sportmate.repository.SportRepository;
import com.example.sportmate.repository.UsersRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.sportmate.config.JWTAuthorizationFilter.PREFIX;
import static com.example.sportmate.config.JWTAuthorizationFilter.SECRET;
import static com.example.sportmate.enumeration.ErrorMessageEnum.*;
import static com.example.sportmate.mapper.ActivityMapper.buildActivity;
import static com.example.sportmate.mapper.ActivityMapper.buildActivityResponseDto;

@Service
@AllArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UsersRepository usersRepository;
    private final SportRepository sportRepository;
    private final LevelRepository levelRepository;

    public ResponseEntity<ResponseDefaultDto> deleteActivity(final Integer id){
        activityRepository.deleteById(id);
        return new ResponseEntity<>(new ResponseDefaultDto("Activité " + id +" supprimé"), HttpStatus.OK);
    }

    public ActivityResponseDto createActivity(final ActivityRequestDto activityRequestDto, final String token){
        final Sport sport = sportRepository.findByLabel(activityRequestDto.sport())
                .orElseThrow(()-> new NotFoundException(SPORT_NOT_FOUND.getMessage()));
        final Level level = levelRepository.findByLabel(activityRequestDto.activityLevel())
                .orElseThrow(()-> new NotFoundException(LEVEL_NOT_FOUND.getMessage()));
        final Users user = usersRepository.findByEmail(findEmailInToken(token))
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND.getMessage()));
        final Activity activityToSave = buildActivity(activityRequestDto, user, sport, level);
        final Activity activitySaved = activityRepository.save(activityToSave);
        return buildActivityResponseDto(activitySaved, sport, level);

    }

    public ActivityResponseDto getActivity(final Integer id){
        final Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Auncune activité trouvée avec l'id " + id));
        final Sport sport = sportRepository.findById(activity.sport())
                .orElseThrow(()-> new NotFoundException(SPORT_NOT_FOUND.getMessage()));
        final Level level = levelRepository.findById(activity.activityLevel())
                .orElseThrow(()-> new NotFoundException(LEVEL_NOT_FOUND.getMessage()));
        return buildActivityResponseDto(activity, sport, level);
    }

    public List<ActivityResponseDto> getAllActivities(){
        final List<Activity> allActivitiesFind = (List<Activity>) activityRepository.findAll();
        return buildActivityResponseSortByDate(allActivitiesFind);
    }

    private List<ActivityResponseDto> buildActivityResponseSortByDate(final List<Activity> allActivitiesFind) {
        final List<ActivityResponseDto> activityResponse = new ArrayList<>();
        allActivitiesFind.forEach(activity -> {
            final Sport sport = sportRepository.findById(activity.sport())
                    .orElseThrow(()-> new NotFoundException(SPORT_NOT_FOUND.getMessage()));
            final Level level = levelRepository.findById(activity.activityLevel())
                    .orElseThrow(()-> new NotFoundException(LEVEL_NOT_FOUND.getMessage()));
            activityResponse.add(buildActivityResponseDto(activity, sport, level));
        });
        activityResponse.sort(new ActivityResponseDto.DateComparator());
        return activityResponse;
    }

    public List<ActivityResponseDto> getUserActivities(final String token){
        final List<Activity> activitiesByToken = activityRepository.findActivitiesByEmail(findEmailInToken(token));
        return buildActivityResponseSortByDate(activitiesByToken);
    }

    public ActivityResponseDto updateActivity(final ActivityRequestDto activityRequestDto, final Integer id, final String token){
        final Sport sport = sportRepository.findByLabel(activityRequestDto.sport())
                .orElseThrow(()-> new NotFoundException(SPORT_NOT_FOUND.getMessage()));
        final Level level = levelRepository.findByLabel(activityRequestDto.activityLevel())
                .orElseThrow(()-> new NotFoundException(LEVEL_NOT_FOUND.getMessage()));
        final String email = findEmailInToken(token);
        final Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND.getMessage()));
        final Activity activityFind = activityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Auncune activité trouvée avec l'id " + id));

        final Activity activityToSave = buildActivity(activityRequestDto, user, sport, level, activityFind.id());
        final Activity activitySaved = activityRepository.save(activityToSave);
        return buildActivityResponseDto(activitySaved, sport, level);
    }

    private String findEmailInToken(final String token){
        final Claims claims = Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(token.replace(PREFIX, "")).getBody();
        if (claims.get("authorities") != null) {
            return claims.getSubject();
        }
        throw new AuthenticationException("Impossible de récupérer le mail dans le token");
    }
}
