package com.example.sportmate.service;

import com.example.sportmate.DataTest;
import com.example.sportmate.entity.Activity;
import com.example.sportmate.entity.Level;
import com.example.sportmate.entity.Sport;
import com.example.sportmate.entity.Users;
import com.example.sportmate.exception.NotFoundException;
import com.example.sportmate.mapper.ActivityMapper;
import com.example.sportmate.record.ActivityRequestDto;
import com.example.sportmate.record.ActivityResponseDto;
import com.example.sportmate.record.ResponseDefaultDto;
import com.example.sportmate.repository.ActivityRepository;
import com.example.sportmate.repository.LevelRepository;
import com.example.sportmate.repository.SportRepository;
import com.example.sportmate.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static com.example.sportmate.mapper.ActivityMapper.buildActivity;
import static com.example.sportmate.mapper.ActivityMapper.buildActivityResponseDto;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ActivityServiceTest implements DataTest {
    @Autowired
    ActivityService activityService;

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
        ActivityRequestDto activityRequestDto = new ActivityRequestDto(false, ACTIVITY_NAME, ACTIVITY_DATE, ADRESS,
                LONGITUDE, LATITUDE, PARTICIPANT, SPORT_NAME, LEVEL_NAME, DESCRIPTION, CONTACT);
        when(sportRepository.findByLabel(SPORT_NAME)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> activityService.createActivity(activityRequestDto, loginService.getJWTToken(EMAIL)))
                .hasMessageContaining("Sport non trouvé")
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void createActivity_should_not_find_level_so_throw_NotFoundException() {
        ActivityRequestDto activityRequestDto = new ActivityRequestDto(false, ACTIVITY_NAME, ACTIVITY_DATE, ADRESS,
                LONGITUDE, LATITUDE, PARTICIPANT, SPORT_NAME, LEVEL_NAME, DESCRIPTION, CONTACT);

        when(sportRepository.findByLabel(SPORT_NAME)).thenReturn(Optional.of(new Sport(null, SPORT_NAME)));
        when(levelRepository.findByLabel(LEVEL_NAME)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> activityService.createActivity(activityRequestDto, loginService.getJWTToken(EMAIL)))
                .hasMessageContaining("Niveau non trouvé")
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void createActivity_should_not_find_user_so_throw_NotFoundException() {
        ActivityRequestDto activityRequestDto = new ActivityRequestDto(false, ACTIVITY_NAME, ACTIVITY_DATE, ADRESS,
                LONGITUDE, LATITUDE, PARTICIPANT, SPORT_NAME, LEVEL_NAME, DESCRIPTION, CONTACT);

        when(sportRepository.findByLabel(SPORT_NAME)).thenReturn(Optional.of(new Sport(null, SPORT_NAME)));
        when(levelRepository.findByLabel(LEVEL_NAME)).thenReturn(Optional.of(new Level(null, LEVEL_NAME)));
        when(usersRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> activityService.createActivity(activityRequestDto, loginService.getJWTToken(EMAIL)))
                .hasMessageContaining("Utilisteur non trouvé")
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void createActivity_should_save_new_user() {
        ActivityRequestDto activityRequestDto = new ActivityRequestDto(false, ACTIVITY_NAME, ACTIVITY_DATE, ADRESS,
                LONGITUDE, LATITUDE, PARTICIPANT, SPORT_NAME, LEVEL_NAME, DESCRIPTION, CONTACT);
        Sport sport = new Sport(null, SPORT_NAME);
        Level level = new Level(null, LEVEL_NAME);
        Users user = DataTest.buildNewUser();
        when(sportRepository.findByLabel(SPORT_NAME)).thenReturn(Optional.of(sport));
        when(levelRepository.findByLabel(LEVEL_NAME)).thenReturn(Optional.of(level));
        when(usersRepository.findByEmail(EMAIL)).thenReturn(Optional.of(user));
        Activity activity = DataTest.buildActivity(ID);
        when(activityRepository.save(ActivityMapper.buildActivity(activityRequestDto, user, sport, level))).thenReturn(activity);

        ActivityResponseDto activitySaved = activityService.createActivity(activityRequestDto, loginService.getJWTToken(EMAIL));
        assertThat(activitySaved).isEqualTo(buildActivityResponseDto(activity));
    }

    @Test
    void getActivity_should_find_activity() {
        Activity activity = DataTest.buildActivity();
        when(activityRepository.findById(ID)).thenReturn(Optional.of(activity));
        ActivityResponseDto activityResponseDto = activityService.getActivity(ID);
        assertThat(activityResponseDto).isEqualTo(buildActivityResponseDto(activity));
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
        Activity activity = DataTest.buildActivity();
        when(activityRepository.findAll()).thenReturn(singletonList(activity));
        List<ActivityResponseDto> allActivities = activityService.getAllActivities();
        assertThat(allActivities).isEqualTo(singletonList(buildActivityResponseDto(activity)));
    }

    @Test
    void getUserActivities() {
        Activity activity = DataTest.buildActivity();
        when(activityRepository.findActivitiesByEmail(EMAIL)).thenReturn(singletonList(activity));
        List<ActivityResponseDto> allActivities = activityService.getUserActivities(loginService.getJWTToken(EMAIL));
        assertThat(allActivities).isEqualTo(singletonList(buildActivityResponseDto(activity)));
    }

    @Test
    void updateActivity_should_not_find_sport_so_throw_NotFoundException() {
        ActivityRequestDto activityRequestDto = new ActivityRequestDto(false, ACTIVITY_NAME, ACTIVITY_DATE, ADRESS,
                LONGITUDE, LATITUDE, PARTICIPANT, SPORT_NAME, LEVEL_NAME, DESCRIPTION, CONTACT);
        when(sportRepository.findByLabel(SPORT_NAME)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> activityService.updateActivity(activityRequestDto, ID, loginService.getJWTToken(EMAIL)))
                .hasMessageContaining("Sport non trouvé")
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void updateActivity_should_not_find_level_so_throw_NotFoundException() {
        ActivityRequestDto activityRequestDto = new ActivityRequestDto(false, ACTIVITY_NAME, ACTIVITY_DATE, ADRESS,
                LONGITUDE, LATITUDE, PARTICIPANT, SPORT_NAME, LEVEL_NAME, DESCRIPTION, CONTACT);

        when(sportRepository.findByLabel(SPORT_NAME)).thenReturn(Optional.of(new Sport(null, SPORT_NAME)));
        when(levelRepository.findByLabel(LEVEL_NAME)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> activityService.updateActivity(activityRequestDto, ID, loginService.getJWTToken(EMAIL)))
                .hasMessageContaining("Niveau non trouvé")
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void updateActivity_should_not_find_user_so_throw_NotFoundException() {
        ActivityRequestDto activityRequestDto = new ActivityRequestDto(false, ACTIVITY_NAME, ACTIVITY_DATE, ADRESS,
                LONGITUDE, LATITUDE, PARTICIPANT, SPORT_NAME, LEVEL_NAME, DESCRIPTION, CONTACT);

        when(sportRepository.findByLabel(SPORT_NAME)).thenReturn(Optional.of(new Sport(null, SPORT_NAME)));
        when(levelRepository.findByLabel(LEVEL_NAME)).thenReturn(Optional.of(new Level(null, LEVEL_NAME)));
        when(usersRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> activityService.updateActivity(activityRequestDto, ID, loginService.getJWTToken(EMAIL)))
                .hasMessageContaining("Utilisteur non trouvé")
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void updateActivity_should_not_find_activity_so_throw_NotFoundException() {
        ActivityRequestDto activityRequestDto = new ActivityRequestDto(false, ACTIVITY_NAME, ACTIVITY_DATE, ADRESS,
                LONGITUDE, LATITUDE, PARTICIPANT, SPORT_NAME, LEVEL_NAME, DESCRIPTION, CONTACT);

        when(sportRepository.findByLabel(SPORT_NAME)).thenReturn(Optional.of(new Sport(null, SPORT_NAME)));
        when(levelRepository.findByLabel(LEVEL_NAME)).thenReturn(Optional.of(new Level(null, LEVEL_NAME)));
        when(usersRepository.findByEmail(EMAIL)).thenReturn(Optional.of(DataTest.buildNewUser()));
        when(activityRepository.findById(ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> activityService.updateActivity(activityRequestDto, ID, loginService.getJWTToken(EMAIL)))
                .hasMessageContaining("Auncune activité trouvée avec l'id " + ID)
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void updateActivity_should_save_new_user() {
        ActivityRequestDto activityRequestDto = new ActivityRequestDto(false, ACTIVITY_NAME, ACTIVITY_DATE, ADRESS,
                LONGITUDE, LATITUDE, PARTICIPANT, SPORT_NAME, LEVEL_NAME, DESCRIPTION, CONTACT);

        Sport sport = new Sport(null, SPORT_NAME);
        Level level = new Level(null, LEVEL_NAME);
        Users user = DataTest.buildNewUser();
        when(sportRepository.findByLabel(SPORT_NAME)).thenReturn(Optional.of(sport));
        when(levelRepository.findByLabel(LEVEL_NAME)).thenReturn(Optional.of(level));
        when(usersRepository.findByEmail(EMAIL)).thenReturn(Optional.of(user));
        Activity activity = DataTest.buildActivity(ID);
        when(activityRepository.findById(ID)).thenReturn(Optional.of(activity));
        when(activityRepository.save(ActivityMapper.buildActivity(activityRequestDto, user, sport, level, activity.id()))).thenReturn(activity);

        ActivityResponseDto activitySaved = activityService.updateActivity(activityRequestDto, ID, loginService.getJWTToken(EMAIL));
        assertThat(activitySaved).isEqualTo(buildActivityResponseDto(activity));
    }

    @Test
    void deleteActivity_should_delete_new_activity_saved() {
        instantiateAndSaveNewActivity();
        ResponseEntity<ResponseDefaultDto> response = activityService.deleteActivity(ID);
        assertThat(response).isEqualTo(new ResponseEntity<>(new ResponseDefaultDto("Activité " + ID + " supprimé"), HttpStatus.OK));

    }

    private void instantiateAndSaveNewActivity() {
        final Activity activity = new Activity(null, IS_EVENT, ACTIVITY_NAME, ACTIVITY_DATE, ID,
                ADRESS, LONGITUDE, LATITUDE, PARTICIPANT, SPORT_ID, LEVEL_ID, DESCRIPTION, CONTACT, CREATED_DATE, null);
        activityRepository.save(activity);
    }
}