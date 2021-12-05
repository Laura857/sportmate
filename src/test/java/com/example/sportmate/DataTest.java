package com.example.sportmate;

import com.example.sportmate.entity.Users;
import com.example.sportmate.enumeration.Sex;

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
      String PASSWORD = "un_mot_de_passe";
      String LAST_NAME = "Pierre";
      String FIRST_NAME = "Dupont";
      String MOBILE = "0606060606";
      String PROFILE_PICTURE = "Natation";
      Sex SEX = FEMME;
      boolean CONSENTS = false;
      LocalDate BIRTHDAY = LocalDate.now();

      static Users buildNewUser(){
        return new Users(null, generateUniqueEmail(), PASSWORD, LAST_NAME, FIRST_NAME, MOBILE, PROFILE_PICTURE,
                SEX, BIRTHDAY, CONSENTS, CREATED_DATE, null);
    }

}
