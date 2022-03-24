package com.example.sportmate.mapper;

import com.example.sportmate.entity.Users;
import com.example.sportmate.record.authentification.signin.SigningRequestDto;
import com.example.sportmate.record.user.UserDataDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UsersMapper {
    public static Users buildUsers(final SigningRequestDto signInRequest, final String password){
        return new Users(
                null,
                signInRequest.login().email(),
                password,
                signInRequest.user().lastName(),
                signInRequest.user().firstName(),
                signInRequest.user().mobilePhone(),
                signInRequest.user().profilePicture(),
                signInRequest.user().genre(),
                signInRequest.user().birthday(),
                signInRequest.user().consents(),
                LocalDate.now(),
                null);
    }

    public static Users buildUsers(final UserDataDto userRequest, final Users userSaved){
        return new Users(
                userSaved.id(),
                userRequest.email(),
                userSaved.password(),
                userRequest.lastName(),
                userRequest.firstName(),
                userRequest.mobilePhone(),
                userRequest.profilePicture(),
                userRequest.genre(),
                userRequest.birthday(),
                userRequest.consents(),
                userSaved.created(),
                LocalDate.now());
    }

    public static Users buildUsers(final Users userSaved, final String password){
        return new Users(
                userSaved.id(),
                userSaved.email(),
                password,
                userSaved.lastName(),
                userSaved.firstName(),
                userSaved.mobile(),
                userSaved.profilePicture(),
                userSaved.genre(),
                userSaved.birthday(),
                userSaved.consents(),
                userSaved.created(),
                LocalDate.now());
    }

    public static UserDataDto buildUserData(final Users user){
        return new UserDataDto(
                user.profilePicture(),
                user.consents(),
                user.email(),
                user.lastName(),
                user.firstName(),
                user.genre(),
                user.birthday(),
                user.mobile());
    }
}
