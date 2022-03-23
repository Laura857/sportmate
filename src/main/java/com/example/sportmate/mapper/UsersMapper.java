package com.example.sportmate.mapper;

import com.example.sportmate.entity.Users;
import com.example.sportmate.record.signin.SigningRequestDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
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
