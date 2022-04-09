package com.example.sportmate;

import com.example.sportmate.entity.Activity;
import com.example.sportmate.entity.Level;
import com.example.sportmate.entity.Sport;
import com.example.sportmate.entity.Users;
import com.example.sportmate.enumeration.GenreEnum;
import com.example.sportmate.record.activity.ActivityRequestDto;
import com.example.sportmate.record.authentification.signing.UserRequestDto;
import com.example.sportmate.record.user.UserDataDto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.example.sportmate.enumeration.GenreEnum.FEMME;
import static com.example.sportmate.repository.UsersRepositoryTest.generateUniqueEmail;

public interface DataTest {
    String ACTIVITY_NAME = "Cours de natation en pleine air";
    String TOKEN_WITH_BEARER_PREFIX = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJzb2Z0dGVrSldUIiwic3ViIjoibGF1cmFAZ21haWwuY29tIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTYzNzk1NTU4OSwiZXhwIjoxNjM3OTU2MTg5fQ.t39C9u6Ydq2jj1Wn2HuMLqUROi2d_0Jj8RHcfpFpvYoT-Xh45aBE7sEbZDwB-fMX7v3iegZgU7nNp2Y2bca2kw";
    String TOKEN_WITHOUT_BEARER_PREFIX = "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJzb2Z0dGVrSldUIiwic3ViIjoibGF1cmFAZ21haWwuY29tIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTYzNzk1NTU4OSwiZXhwIjoxNjM3OTU2MTg5fQ.t39C9u6Ydq2jj1Wn2HuMLqUROi2d_0Jj8RHcfpFpvYoT-Xh45aBE7sEbZDwB-fMX7v3iegZgU7nNp2Y2bca2kw";
    LocalDateTime ACTIVITY_DATE = LocalDateTime.of(2022, 1, 12, 13, 10, 2);
    boolean IS_EVENT = false;
    String ADDRESS = "Piscine Monge";
    String LONGITUDE = "123.9302.03230";
    String LATITUDE = "123.9302.03230";
    Integer PARTICIPANT = 10;
    int LEVEL_ID = 1;
    String CONTACT = "0606060606";
    String DESCRIPTION = "description";
    int SPORT_ID = 1;
    LocalDate CREATED_DATE = LocalDate.now();
    String LEVEL_NAME = "Débutant";
    String SPORT_NAME = "Natation";
    String HOBBIES = "Cinéma";
    String EMAIL = "test@gmail.com";
    String EMAIL_OTHER = "other@gmail.com";
    String PASSWORD = "unM@t2PasseS0lid3";
    String PASSWORD_OTHER = "un_mot_de_passe0bis";
    String LAST_NAME = "Pierre";
    String FIRST_NAME = "Dupont";
    String MOBILE = "0606060606";
    String PROFILE_PICTURE = "Natation";
    GenreEnum GENRE = FEMME;
    boolean CONSENTS = false;
    LocalDate BIRTHDAY = LocalDate.now();
    Integer ID = 1;

    static Users buildNewUser() {
        return new Users(null, generateUniqueEmail(), PASSWORD, LAST_NAME, FIRST_NAME, MOBILE, PROFILE_PICTURE,
                GENRE, BIRTHDAY, CONSENTS, CREATED_DATE, null);
    }

    static UserDataDto buildDefaultUserData() {
        return new UserDataDto(
                PROFILE_PICTURE,
                CONSENTS,
                EMAIL,
                LAST_NAME,
                FIRST_NAME,
                GENRE,
                BIRTHDAY,
                MOBILE);
    }

    static UserDataDto buildDefaultUserData(final String email) {
        return new UserDataDto(
                PROFILE_PICTURE,
                CONSENTS,
                email,
                LAST_NAME,
                FIRST_NAME,
                GENRE,
                BIRTHDAY,
                MOBILE);
    }

    static Users buildNewUserDefault() {
        return new Users(ID, EMAIL, PASSWORD, LAST_NAME, FIRST_NAME, MOBILE, PROFILE_PICTURE, GENRE, BIRTHDAY,
                CONSENTS, LocalDate.now(), null);
    }

    static Activity buildActivity() {
        return new Activity(null, IS_EVENT, ACTIVITY_NAME, ACTIVITY_DATE, ID,
                ADDRESS, LONGITUDE, LATITUDE, PARTICIPANT, SPORT_ID, LEVEL_ID, DESCRIPTION, CONTACT, CREATED_DATE, null);
    }

    static Activity buildActivity(final Integer id) {
        return new Activity(id, IS_EVENT, ACTIVITY_NAME, ACTIVITY_DATE, ID,
                ADDRESS, LONGITUDE, LATITUDE, PARTICIPANT, SPORT_ID, LEVEL_ID, DESCRIPTION, CONTACT, CREATED_DATE, null);
    }

    static Sport buildSport() {
        return new Sport(null, SPORT_NAME);
    }

    static Level buildLevel() {
        return new Level(null, LEVEL_NAME);
    }

    static ActivityRequestDto buildDefaultActivityRequest() {
        return new ActivityRequestDto(false, ACTIVITY_NAME, ACTIVITY_DATE, ADDRESS,
                LONGITUDE, LATITUDE, PARTICIPANT, SPORT_NAME, LEVEL_NAME, CONTACT, DESCRIPTION);
    }

    static UserRequestDto buildDefaultUserRequest() {
        return new UserRequestDto(PROFILE_PICTURE, CONSENTS, LAST_NAME, FIRST_NAME, GENRE, BIRTHDAY, MOBILE);
    }

    static Users buildDefaultUsers() {
        return new Users(null, EMAIL, PASSWORD, LAST_NAME, FIRST_NAME, MOBILE, PROFILE_PICTURE, GENRE, BIRTHDAY,
                CONSENTS, LocalDate.now(), null);
    }

}
