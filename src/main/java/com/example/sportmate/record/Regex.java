package com.example.sportmate.record;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Regex {
    public static final String EMAIL = "^[a-z0-9!#$%&’*+-/=?`{|}~_]+(?:\\.[a-z0-9!#$%&’*+-/=?`{|}~_]+)*@(?:[a-z0-9-]+\\.)+[a-z]{2,6}$";
    public static final String MOBILE_PHONE = "^(0)(6|7)[0-9]{8}$";
    public static final String LOCAL_DATE = "yyyy-MM-dd";
    public static final String PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
}
