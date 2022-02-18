package com.example.sportmate.mapper;

import com.example.sportmate.entity.Users;
import com.example.sportmate.record.signin.SigningRequestDto;

import java.time.LocalDate;

public class UsersMapper {
    public static Users buildUsers(final SigningRequestDto signInRequest, final String password){
        return new Users(null,
                signInRequest.login().email(),
                password,
                signInRequest.user().lastName(),
                signInRequest.user().firstName(),
                signInRequest.user().mobilePhone(),
                signInRequest.user().profilePicture(),
                signInRequest.user().genre(),
                signInRequest.user().birthday(),
                false,
                LocalDate.now(),
                null);
    }
}
