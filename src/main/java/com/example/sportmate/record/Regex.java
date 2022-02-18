package com.example.sportmate.record;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Regex {
    public static final String EMAIL = "^[a-z0-9!#$%&’*+-/=?`{|}~_]+(?:\\.[a-z0-9!#$%&’*+-/=?`{|}~_]+)*@(?:[a-z0-9-]+\\.)+[a-z]{2,6}$";
    public static final String MOBILE_PHONE = "^(0)(6|7)[0-9]{8}$";
    public static final String LOCAL_DATE = "yyyy-MM-dd";
}
