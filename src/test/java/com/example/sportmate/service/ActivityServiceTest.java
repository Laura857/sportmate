package com.example.sportmate.service;

import com.example.sportmate.DataTest;
import com.example.sportmate.entity.Activity;
import com.example.sportmate.entity.Level;
import com.example.sportmate.entity.Sport;
import com.example.sportmate.entity.Users;
import com.example.sportmate.exception.AuthenticationException;
import com.example.sportmate.exception.NotFoundException;
import com.example.sportmate.mapper.ActivityMapper;
import com.example.sportmate.record.ResponseDefaultDto;
import com.example.sportmate.record.activity.ActivityRequestDto;
import com.example.sportmate.record.activity.ActivityResponseDto;
import com.example.sportmate.repository.ActivityRepository;
import com.example.sportmate.repository.LevelRepository;
import com.example.sportmate.repository.SportRepository;
import com.example.sportmate.repository.UsersRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.example.sportmate.DataTest.buildDefaultActivityRequest;
import static com.example.sportmate.enumeration.ErrorMessageEnum.*;
import static com.example.sportmate.mapper.ActivityMapper.buildActivityResponseDto;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@SpringBootTest
class ActivityServiceTest implements DataTest {
    @MockBean
    private final ActivityRepository activityRepository;
    @MockBean
    private final UsersRepository usersRepository;
    @MockBean
    private final SportRepository sportRepository;
    @MockBean
    private final LevelRepository levelRepository;
    private final LoginService loginService;
    @Autowired
    ActivityService activityService;

    @Autowired
    public ActivityServiceTest(final ActivityRepository activityRepository,
                               final UsersRepository usersRepository,
                               final SportRepository sportRepository,
                               final LevelRepository levelRepository,
                               final LoginService loginService) {
        this.activityRepository = activityRepository;
        this.usersRepository = usersRepository;
        this.sportRepository = sportRepository;
        this.levelRepository = levelRepository;
        this.loginService = loginService;
    }

    @Test
    void createActivity_should_not_find_sport_so_throw_NotFoundException() {
        final ActivityRequestDto activityRequestDto = buildDefaultActivityRequest();
        when(sportRepository.findByLabel(SPORT_NAME_SWIM)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> activityService.createActivity(activityRequestDto, loginService.getJWTToken(EMAIL)))
                .hasMessageContaining(SPORT_NOT_FOUND.getMessage())
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void createActivity_should_not_find_level_so_throw_NotFoundException() {
        final ActivityRequestDto activityRequestDto = buildDefaultActivityRequest();

        when(sportRepository.findByLabel(SPORT_NAME_SWIM)).thenReturn(Optional.of(new Sport(null, SPORT_NAME_SWIM)));
        when(levelRepository.findByLabel(LEVEL_NAME_BEGINNING)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> activityService.createActivity(activityRequestDto, loginService.getJWTToken(EMAIL)))
                .hasMessageContaining(LEVEL_NOT_FOUND.getMessage())
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void createActivity_should_not_find_user_so_throw_NotFoundException() {
        final ActivityRequestDto activityRequestDto = buildDefaultActivityRequest();

        when(sportRepository.findByLabel(SPORT_NAME_SWIM)).thenReturn(Optional.of(new Sport(null, SPORT_NAME_SWIM)));
        when(levelRepository.findByLabel(LEVEL_NAME_BEGINNING)).thenReturn(Optional.of(new Level(null, LEVEL_NAME_BEGINNING)));
        when(usersRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> activityService.createActivity(activityRequestDto, loginService.getJWTToken(EMAIL)))
                .hasMessageContaining(USER_NOT_FOUND.getMessage())
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void createActivity_should_save_new_user() {
        final ActivityRequestDto activityRequestDto = buildDefaultActivityRequest();
        final Sport sport = new Sport(null, SPORT_NAME_SWIM);
        final Level level = new Level(null, LEVEL_NAME_BEGINNING);
        final Users user = DataTest.buildNewUser();
        when(sportRepository.findByLabel(SPORT_NAME_SWIM)).thenReturn(Optional.of(sport));
        when(levelRepository.findByLabel(LEVEL_NAME_BEGINNING)).thenReturn(Optional.of(level));
        when(usersRepository.findByEmail(EMAIL)).thenReturn(Optional.of(user));
        final Activity activity = DataTest.buildActivity(ID);
        when(activityRepository.save(ActivityMapper.buildActivity(activityRequestDto, user, sport, level))).thenReturn(activity);

        final ActivityResponseDto activitySaved = activityService.createActivity(activityRequestDto, loginService.getJWTToken(EMAIL));
        assertThat(activitySaved).isEqualTo(buildActivityResponseDto(activity, sport, level));
    }

    @Test
    void getActivity_should_find_activity() {
        final Activity activity = DataTest.buildActivity();
        final Sport sport = DataTest.buildSport();
        final Level level = DataTest.buildLevel();
        when(activityRepository.findById(ID)).thenReturn(Optional.of(activity));
        when(sportRepository.findById(activity.sport())).thenReturn(Optional.of(sport));
        when(levelRepository.findById(activity.activityLevel())).thenReturn(Optional.of(level));
        final ActivityResponseDto activityResponseDto = activityService.getActivity(ID);
        assertThat(activityResponseDto).isEqualTo(buildActivityResponseDto(activity, sport, level));
    }

    @Test
    void getActivity_should_not_find_activity_so_throw_NotFoundException() {
        when(activityRepository.findById(ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> activityService.getActivity(ID))
                .hasMessageContaining("Auncune activité trouvée avec l'id " + ID)
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void getAllActivities_should_retourne_all_activity_saved() {
        final Activity activity = DataTest.buildActivity();
        final Sport sport = DataTest.buildSport();
        final Level level = DataTest.buildLevel();
        when(activityRepository.findAll()).thenReturn(singletonList(activity));
        when(sportRepository.findById(activity.sport())).thenReturn(Optional.of(sport));
        when(levelRepository.findById(activity.activityLevel())).thenReturn(Optional.of(level));
        final List<ActivityResponseDto> allActivities = activityService.getAllActivities();
        assertThat(allActivities).isEqualTo(singletonList(buildActivityResponseDto(activity, sport, level)));
    }

    @Test
    void getUserActivities() {
        final Activity activity = DataTest.buildActivity();
        final Sport sport = DataTest.buildSport();
        final Level level = DataTest.buildLevel();
        when(activityRepository.findActivitiesByEmail(EMAIL)).thenReturn(singletonList(activity));
        when(sportRepository.findById(activity.sport())).thenReturn(Optional.of(sport));
        when(levelRepository.findById(activity.activityLevel())).thenReturn(Optional.of(level));
        final List<ActivityResponseDto> allActivities = activityService.getUserActivities(loginService.getJWTToken(EMAIL));
        assertThat(allActivities).isEqualTo(singletonList(buildActivityResponseDto(activity, sport, level)));
    }

    @Test
    void updateActivity_should_not_find_sport_so_throw_NotFoundException() {
        final ActivityRequestDto activityRequestDto = buildDefaultActivityRequest();
        when(sportRepository.findByLabel(SPORT_NAME_SWIM)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> activityService.updateActivity(activityRequestDto, ID, loginService.getJWTToken(EMAIL)))
                .hasMessageContaining(SPORT_NOT_FOUND.getMessage())
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void updateActivity_should_not_find_level_so_throw_NotFoundException() {
        final ActivityRequestDto activityRequestDto = buildDefaultActivityRequest();

        when(sportRepository.findByLabel(SPORT_NAME_SWIM)).thenReturn(Optional.of(new Sport(null, SPORT_NAME_SWIM)));
        when(levelRepository.findByLabel(LEVEL_NAME_BEGINNING)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> activityService.updateActivity(activityRequestDto, ID, loginService.getJWTToken(EMAIL)))
                .hasMessageContaining(LEVEL_NOT_FOUND.getMessage())
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void updateActivity_should_not_find_user_so_throw_NotFoundException() {
        final ActivityRequestDto activityRequestDto = buildDefaultActivityRequest();

        when(sportRepository.findByLabel(SPORT_NAME_SWIM)).thenReturn(Optional.of(new Sport(null, SPORT_NAME_SWIM)));
        when(levelRepository.findByLabel(LEVEL_NAME_BEGINNING)).thenReturn(Optional.of(new Level(null, LEVEL_NAME_BEGINNING)));
        when(usersRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> activityService.updateActivity(activityRequestDto, ID, loginService.getJWTToken(EMAIL)))
                .hasMessageContaining(USER_NOT_FOUND.getMessage())
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void updateActivity_should_not_find_activity_so_throw_NotFoundException() {
        final ActivityRequestDto activityRequestDto = buildDefaultActivityRequest();

        when(sportRepository.findByLabel(SPORT_NAME_SWIM)).thenReturn(Optional.of(new Sport(null, SPORT_NAME_SWIM)));
        when(levelRepository.findByLabel(LEVEL_NAME_BEGINNING)).thenReturn(Optional.of(new Level(null, LEVEL_NAME_BEGINNING)));
        when(usersRepository.findByEmail(EMAIL)).thenReturn(Optional.of(DataTest.buildNewUser()));
        when(activityRepository.findById(ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> activityService.updateActivity(activityRequestDto, ID, loginService.getJWTToken(EMAIL)))
                .hasMessageContaining("Auncune activité trouvée avec l'id " + ID)
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void updateActivity_should_save_new_user() {
        final ActivityRequestDto activityRequestDto = buildDefaultActivityRequest();

        final Sport sport = new Sport(null, SPORT_NAME_SWIM);
        final Level level = new Level(null, LEVEL_NAME_BEGINNING);
        final Users user = DataTest.buildNewUser();
        when(sportRepository.findByLabel(SPORT_NAME_SWIM)).thenReturn(Optional.of(sport));
        when(levelRepository.findByLabel(LEVEL_NAME_BEGINNING)).thenReturn(Optional.of(level));
        when(usersRepository.findByEmail(EMAIL)).thenReturn(Optional.of(user));
        final Activity activity = DataTest.buildActivity(ID);
        when(activityRepository.findById(ID)).thenReturn(Optional.of(activity));
        when(activityRepository.save(ActivityMapper.buildActivity(activityRequestDto, user, sport, level, activity.id()))).thenReturn(activity);

        final ActivityResponseDto activitySaved = activityService.updateActivity(activityRequestDto, ID, loginService.getJWTToken(EMAIL));
        assertThat(activitySaved).isEqualTo(buildActivityResponseDto(activity, sport, level));
    }

    @Test
    void updateActivity_should_throw_exception_when_email_is_not_present_in_token() {
        final ActivityRequestDto activityRequestDto = buildDefaultActivityRequest();

        final Sport sport = new Sport(null, SPORT_NAME_SWIM);
        final Level level = new Level(null, LEVEL_NAME_BEGINNING);
        final Users user = DataTest.buildNewUser();
        when(sportRepository.findByLabel(SPORT_NAME_SWIM)).thenReturn(Optional.of(sport));
        when(levelRepository.findByLabel(LEVEL_NAME_BEGINNING)).thenReturn(Optional.of(level));
        when(usersRepository.findByEmail(EMAIL)).thenReturn(Optional.of(user));
        final Activity activity = DataTest.buildActivity(ID);
        when(activityRepository.findById(ID)).thenReturn(Optional.of(activity));
        when(activityRepository.save(ActivityMapper.buildActivity(activityRequestDto, user, sport, level, activity.id()))).thenReturn(activity);

        final String secretKey = "mySecretKey";
        final List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        final String token = Jwts.builder()
                .setId("softtekJWT")
                .setSubject(EMAIL)
                .claim("unauthorized",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        assertThatThrownBy(() -> activityService.updateActivity(activityRequestDto, ID, token))
                .hasMessage("Impossible de récupérer le mail dans le token")
                .isInstanceOf(AuthenticationException.class);
    }

    @Test
    void deleteActivity_should_delete_new_activity_saved() {
        instantiateAndSaveNewActivity();
        final ResponseEntity<ResponseDefaultDto> response = activityService.deleteActivity(ID);
        assertThat(response).isEqualTo(new ResponseEntity<>(new ResponseDefaultDto("Activité " + ID + " supprimé"), HttpStatus.OK));

    }

    private void instantiateAndSaveNewActivity() {
        final Activity activity = new Activity(null, IS_EVENT, ACTIVITY_NAME, ACTIVITY_DATE, ID,
                ADDRESS, LONGITUDE, LATITUDE, PARTICIPANT, SPORT_ID, LEVEL_ID, DESCRIPTION, CONTACT, CREATED_DATE, null);
        activityRepository.save(activity);
    }
}