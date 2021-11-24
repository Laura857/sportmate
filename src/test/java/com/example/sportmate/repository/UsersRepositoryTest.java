package com.example.sportmate.repository;

import com.example.sportmate.entity.Activity;
import com.example.sportmate.entity.Users;
import com.example.sportmate.enumeration.Sex;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.Optional;

import static com.example.sportmate.enumeration.Sex.FEMME;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UsersRepositoryTest {
    public static final String EMAIL = "test@gmail.com";
    public static final String PASSWORD = "un_mot_de_passe";
    public static final String TOKEN = null;
    public static final String LAST_NAME = "Pierre";
    public static final String FIRST_NAME = "Dupont";
    public static final String MOBILE = "0606060606";
    public static final String PROFILE_PICTURE = "Natation";
    public static final Sex SEX = FEMME;
    public static final boolean CONSENTS = false;
    public static final LocalDate BIRTHDAY = LocalDate.now();
    public static final LocalDate CREATED_DATE = LocalDate.now();
    private static int uniqueIdForEmail = 1;

    private final UsersRepository usersRepository;

    @Autowired
    UsersRepositoryTest(final UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Test
    void findById_should_find_a_new_saved_users() {
        String uniqueEmailGenerated = generateUniqueEmail();
        int usersSavedId = instantiateAndSaveNewUsers(uniqueEmailGenerated);
        Optional<Users> usersFind = usersRepository.findById(usersSavedId);
        assertThat(usersFind).isPresent();
        assertThat(usersFind.get()).isEqualTo(new Users(usersSavedId, uniqueEmailGenerated, PASSWORD, TOKEN, LAST_NAME, FIRST_NAME, MOBILE, PROFILE_PICTURE,
                SEX, BIRTHDAY, CONSENTS, CREATED_DATE, null));
    }

    private int instantiateAndSaveNewUsers(String uniqueEmailGenerated) {
        final Users users = new Users(null, uniqueEmailGenerated, PASSWORD, TOKEN, LAST_NAME, FIRST_NAME, MOBILE, PROFILE_PICTURE,
                SEX, BIRTHDAY, CONSENTS, CREATED_DATE, null);
        return usersRepository.save(users).id();
    }

    public static String generateUniqueEmail() {
        return (uniqueIdForEmail++) + EMAIL;
    }

    @Test
    void findById_should_not_find_an_unsaved_users() {
        Optional<Users> usersFind = usersRepository.findById(-1);
        assertThat(usersFind).isEmpty();
    }

    @Test
    void findById_should_not_find_a_deleted_users() {
        int usersSavedId = instantiateAndSaveNewUsers(generateUniqueEmail());
        usersRepository.deleteById(usersSavedId);
        Optional<Users> usersFind = usersRepository.findById(usersSavedId);
        assertThat(usersFind).isEmpty();
    }
}