package com.example.sportmate.entity;

import org.springframework.data.annotation.Id;

public record UserActivity(
        @Id Integer fk_id_user,

        Integer fk_id_activity) {
}
