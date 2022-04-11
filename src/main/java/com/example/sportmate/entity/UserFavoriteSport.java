package com.example.sportmate.entity;

import org.springframework.data.annotation.Id;

public record UserFavoriteSport(
        @Id
        Integer userId,

        Integer sportId,

        Integer levelId) {
}
