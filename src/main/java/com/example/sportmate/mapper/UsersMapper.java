package com.example.sportmate.mapper;

import com.example.sportmate.entity.Users;
import com.example.sportmate.record.authentification.signing.SigningRequestDto;
import com.example.sportmate.record.user.UserDataDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UsersMapper {
    public static Users buildUsers(final SigningRequestDto signInRequest, final String password) {
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

    public static Users buildUsers(final UserDataDto userRequest, final Users userSaved) {
        return new Users(
                userSaved.getId(),
                userRequest.email(),
                userSaved.getPassword(),
                userRequest.lastName(),
                userRequest.firstName(),
                userRequest.mobilePhone(),
                userRequest.profilePicture(),
                userRequest.genre(),
                userRequest.birthday(),
                userRequest.consents(),
                userSaved.getCreated(),
                LocalDate.now());
    }

    public static Users buildUsers(final Users userSaved, final String password) {
        return new Users(
                userSaved.getId(),
                userSaved.getEmail(),
                password,
                userSaved.getLastName(),
                userSaved.getFirstName(),
                userSaved.getMobile(),
                userSaved.getProfilePicture(),
                userSaved.getGenre(),
                userSaved.getBirthday(),
                userSaved.isConsents(),
                userSaved.getCreated(),
                LocalDate.now());
    }

    public static UserDataDto buildUserData(final Users user) {
        return new UserDataDto(
                user.getProfilePicture(),
                user.isConsents(),
                user.getEmail(),
                user.getLastName(),
                user.getFirstName(),
                user.getGenre(),
                user.getBirthday(),
                user.getMobile());
    }
}
