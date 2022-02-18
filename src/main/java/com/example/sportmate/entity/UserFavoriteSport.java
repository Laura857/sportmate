package com.example.sportmate.entity;

import org.springframework.data.annotation.Id;

public record UserFavoriteSport(
        @Id
        Integer fk_id_user,

        Integer fk_id_sport,

        Integer fk_id_level) {
}
