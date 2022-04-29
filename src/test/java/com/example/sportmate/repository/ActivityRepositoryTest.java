package com.example.sportmate.repository;

import com.example.sportmate.DataTest;
import com.example.sportmate.entity.Activity;
import com.example.sportmate.entity.Level;
import com.example.sportmate.entity.Sport;
import com.example.sportmate.entity.Users;
import com.example.sportmate.repository.activity.ActivityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ActivityRepositoryTest implements DataTest {
    private Activity activitySaved;

    @Autowired
    private ActivityRepository activityRepository;
    @MockBean
    private UsersRepository usersRepository;
    @MockBean
    private LevelRepository levelRepository;
    @MockBean
    private SportRepository sportRepository;

    @BeforeEach
    public void instantiateAndSaveNewActivity() {
        final Sport sport = new Sport(2, SPORT_NAME_SWIM);
//        sportRepository.save(sport);

        final Level level = new Level(4, SPORT_NAME_SWIM);
//        levelRepository.save(level);

        final Users users = DataTest.buildDefaultUsersWithId();
//        usersRepository.save(users);

        activitySaved = new Activity(ID, IS_EVENT, ACTIVITY_NAME, ACTIVITY_DATE, users, ADDRESS,
                LONGITUDE, LATITUDE, PARTICIPANT, sport, level, DESCRIPTION, CONTACT, CREATED_DATE, null);
        activityRepository.save(activitySaved);
    }

    @Test
    void findById_should_find_a_new_saved_activity() {
        final Optional<Activity> activityFind = activityRepository.findById(activitySaved.getId());
        assertThat(activityFind)
                .contains(activitySaved);
    }

    @Test
    void findById_should_not_find_an_unsaved_activity() {
        final Optional<Activity> activityFind = activityRepository.findById(-1);
        assertThat(activityFind)
                .isEmpty();
    }

    @Test
    void findById_should_not_find_a_deleted_activity() {
        activityRepository.deleteById(activitySaved.getId());
        final Optional<Activity> activityFind = activityRepository.findById(activitySaved.getId());
        assertThat(activityFind)
                .isEmpty();
    }
}