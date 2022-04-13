package com.example.sportmate.repository;

import com.example.sportmate.DataTest;
import com.example.sportmate.entity.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UsersRepositoryTest implements DataTest {
    public static int uniqueIdForEmail = 1;

    private final UsersRepository usersRepository;

    @Autowired
    UsersRepositoryTest(final UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Test
    void findById_should_find_a_new_saved_users() {
        final String uniqueEmailGenerated = generateUniqueEmail();
        final int usersSavedId = instantiateAndSaveNewUsers(uniqueEmailGenerated);
        final Optional<Users> usersFind = usersRepository.findById(usersSavedId);
        assertThat(usersFind)
                .isPresent()
                .contains(new Users(usersSavedId, uniqueEmailGenerated, PASSWORD, LAST_NAME, FIRST_NAME, MOBILE, PROFILE_PICTURE,
                GENRE, BIRTHDAY, CONSENTS, CREATED_DATE, null));
    }

    private int instantiateAndSaveNewUsers(final String uniqueEmailGenerated) {
        final Users users = new Users(null, uniqueEmailGenerated, PASSWORD, LAST_NAME, FIRST_NAME, MOBILE, PROFILE_PICTURE,
                GENRE, BIRTHDAY, CONSENTS, CREATED_DATE, null);
        return usersRepository.save(users).id();
    }

    public static String generateUniqueEmail() {
        return (uniqueIdForEmail++) + EMAIL;
    }

    @Test
    void findById_should_not_find_an_unsaved_users() {
        final Optional<Users> usersFind = usersRepository.findById(-1);
        assertThat(usersFind)
                .isEmpty();
    }

    @Test
    void findById_should_not_find_a_deleted_users() {
        final int usersSavedId = instantiateAndSaveNewUsers(generateUniqueEmail());
        usersRepository.deleteById(usersSavedId);
        final Optional<Users> usersFind = usersRepository.findById(usersSavedId);
        assertThat(usersFind)
                .isEmpty();
    }
}