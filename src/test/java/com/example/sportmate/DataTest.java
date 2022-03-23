package com.example.sportmate;

import com.example.sportmate.entity.Activity;
import com.example.sportmate.entity.Level;
import com.example.sportmate.entity.Sport;
import com.example.sportmate.entity.Users;
import com.example.sportmate.enumeration.Genre;

import java.time.LocalDate;

import static com.example.sportmate.enumeration.Genre.FEMME;
import static com.example.sportmate.repository.UsersRepositoryTest.generateUniqueEmail;

public interface DataTest {
    String ACTIVITY_NAME = "Cours de natation en pleine air";
    String TOKEN_WITH_BEARER_PREFIX = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJzb2Z0dGVrSldUIiwic3ViIjoibGF1cmFAZ21haWwuY29tIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTYzNzk1NTU4OSwiZXhwIjoxNjM3OTU2MTg5fQ.t39C9u6Ydq2jj1Wn2HuMLqUROi2d_0Jj8RHcfpFpvYoT-Xh45aBE7sEbZDwB-fMX7v3iegZgU7nNp2Y2bca2kw";;
    String TOKEN_WITHOUT_BEARER_PREFIX = "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJzb2Z0dGVrSldUIiwic3ViIjoibGF1cmFAZ21haWwuY29tIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTYzNzk1NTU4OSwiZXhwIjoxNjM3OTU2MTg5fQ.t39C9u6Ydq2jj1Wn2HuMLqUROi2d_0Jj8RHcfpFpvYoT-Xh45aBE7sEbZDwB-fMX7v3iegZgU7nNp2Y2bca2kw";;;
    LocalDate ACTIVITY_DATE = LocalDate.now();
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
    String PASSWORD = "un_mot_de_passe";
    String LAST_NAME = "Pierre";
    String FIRST_NAME = "Dupont";
    String MOBILE = "0606060606";
    String PROFILE_PICTURE = "Natation";
    Genre GENRE = FEMME;
    boolean CONSENTS = false;
    LocalDate BIRTHDAY = LocalDate.now();
    Integer ID = 1;

    static Users buildNewUser() {
        return new Users(null, generateUniqueEmail(), PASSWORD, LAST_NAME, FIRST_NAME, MOBILE, PROFILE_PICTURE,
                GENRE, BIRTHDAY, CONSENTS, CREATED_DATE, null);
    }

    static Activity buildActivity() {
        return new Activity(null, IS_EVENT, ACTIVITY_NAME, ACTIVITY_DATE, ID,
                ADDRESS, LONGITUDE, LATITUDE, PARTICIPANT, SPORT_ID, LEVEL_ID, DESCRIPTION, CONTACT, CREATED_DATE, null);
    }

    static Activity buildActivity(final Integer id) {
        return new Activity(id, IS_EVENT, ACTIVITY_NAME, ACTIVITY_DATE, ID,
                ADDRESS, LONGITUDE, LATITUDE, PARTICIPANT, SPORT_ID, LEVEL_ID, DESCRIPTION, CONTACT, CREATED_DATE, null);
    }

    static Sport buildSport(){
       return new Sport(null, SPORT_NAME);
    }

    static Level buildLevel(){
       return new Level(null, LEVEL_NAME);
    }
}
