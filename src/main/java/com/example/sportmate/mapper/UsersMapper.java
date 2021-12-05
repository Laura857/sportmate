package com.example.sportmate.mapper;

import com.example.sportmate.entity.Users;
import com.example.sportmate.record.SigninRequestDto;

import java.time.LocalDate;

public class UsersMapper {
    public static Users buildUsers(SigninRequestDto signinRequestDto, String password){
        return new Users(null, signinRequestDto.email(), password, signinRequestDto.lastName(),
                signinRequestDto.firstName(), signinRequestDto.mobile(), signinRequestDto.profilePicture(),
                signinRequestDto.sex(), signinRequestDto.birthday(), signinRequestDto.consents(), LocalDate.now(), null);
    }
}
