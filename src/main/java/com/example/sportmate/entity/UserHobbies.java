package com.example.sportmate.entity;

import java.time.LocalDateTime;

public record UserHobbies(
        Integer userId,
        Integer hobbiesId,
        LocalDateTime created) {
}


