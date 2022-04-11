package com.example.sportmate.entity;

import org.springframework.data.annotation.Id;

public record UserActivity(
        @Id Integer userId,

        Integer activityId) {
}
