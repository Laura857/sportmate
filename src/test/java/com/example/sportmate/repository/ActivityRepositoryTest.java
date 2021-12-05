package com.example.sportmate.repository;

import com.example.sportmate.DataTest;
import com.example.sportmate.entity.Activity;
import com.example.sportmate.entity.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static com.example.sportmate.repository.UsersRepositoryTest.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ActivityRepositoryTest implements DataTest {

    private final ActivityRepository activityRepository;
    private final UsersRepository usersRepository;

    @Autowired
    ActivityRepositoryTest(final ActivityRepository activityRepository, final UsersRepository usersRepository) {
        this.activityRepository = activityRepository;
        this.usersRepository = usersRepository;
    }

    @Test
    void findById_should_find_a_new_saved_activity() {
        Activity activitySaved = instantiateAndSaveNewActivity();
        Optional<Activity> activityFind = activityRepository.findById(activitySaved.id());
        assertThat(activityFind).isPresent();
        assertThat(activityFind).contains(activitySaved);
    }

    private Activity instantiateAndSaveNewActivity() {
        final Users users = DataTest.buildNewUser();
        Integer usersSavedId = usersRepository.save(users).id();
        final Activity activity = new Activity(null, IS_EVENT, ACTIVITY_NAME, ACTIVITY_DATE, usersSavedId,
                ADRESS, LONGITUDE, LATITUDE, PARTICIPANT, SPORT_ID, LEVEL_ID, CREATED_DATE, null);
        return activityRepository.save(activity);
    }

    @Test
    void findById_should_not_find_an_unsaved_activity() {
        Optional<Activity> activityFind = activityRepository.findById(-1);
        assertThat(activityFind).isEmpty();
    }

    @Test
    void findById_should_not_find_a_deleted_activity() {
        Activity activitySaved = instantiateAndSaveNewActivity();
        activityRepository.deleteById(activitySaved.id());
        Optional<Activity> activityFind = activityRepository.findById(activitySaved.id());
        assertThat(activityFind).isEmpty();
    }
}