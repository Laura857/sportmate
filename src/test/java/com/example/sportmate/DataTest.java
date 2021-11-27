package com.example.sportmate;

import com.example.sportmate.entity.Activity;
import com.example.sportmate.entity.Users;
import com.example.sportmate.enumeration.Sex;
import org.springframework.data.relational.core.sql.In;

import java.time.LocalDate;

import static com.example.sportmate.enumeration.Sex.FEMME;
import static com.example.sportmate.repository.UsersRepositoryTest.generateUniqueEmail;

public interface DataTest {
    String ACTIVITY_NAME = "Cours de natation en pleine air";
    LocalDate ACTIVITY_DATE = LocalDate.now();
    boolean IS_EVENT = false;
    String ADRESS = "Piscine Monge";
    String LONGITUDE = "Natation";
    String LATITUDE = "Natation";
    Integer PARTICIPANT = 10;
    int LEVEL_ID = 1;
    int SPORT_ID = 1;
    LocalDate CREATED_DATE = LocalDate.now();
    String LEVEL_NAME = "Débutant";
    String SPORT_NAME = "Natation";
    String EMAIL = "test@gmail.com";
    String TOKEN = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJzb2Z0dGVrSldUIiwic3ViIjoibGF1cmFAZ21haWwuY29tIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTYzNzk1NTU4OSwiZXhwIjoxNjM3OTU2MTg5fQ.t39C9u6Ydq2jj1Wn2HuMLqUROi2d_0Jj8RHcfpFpvYoT-Xh45aBE7sEbZDwB-fMX7v3iegZgU7nNp2Y2bca2kw";
    String PASSWORD = "un_mot_de_passe";
    String LAST_NAME = "Pierre";
    String FIRST_NAME = "Dupont";
    String MOBILE = "0606060606";
    String PROFILE_PICTURE = "Natation";
    Sex SEX = FEMME;
    boolean CONSENTS = false;
    LocalDate BIRTHDAY = LocalDate.now();
    Integer ID = 1;

    static Users buildNewUser() {
        return new Users(null, generateUniqueEmail(), PASSWORD, LAST_NAME, FIRST_NAME, MOBILE, PROFILE_PICTURE,
                SEX, BIRTHDAY, CONSENTS, CREATED_DATE, null);
    }

    static Activity buildActivity() {
        return new Activity(null, IS_EVENT, ACTIVITY_NAME, ACTIVITY_DATE, ID,
                ADRESS, LONGITUDE, LATITUDE, PARTICIPANT, SPORT_ID, LEVEL_ID, CREATED_DATE, null);
    }

    static Activity buildActivity(Integer id) {
        return new Activity(id, IS_EVENT, ACTIVITY_NAME, ACTIVITY_DATE, ID,
                ADRESS, LONGITUDE, LATITUDE, PARTICIPANT, SPORT_ID, LEVEL_ID, CREATED_DATE, null);
    }

}
