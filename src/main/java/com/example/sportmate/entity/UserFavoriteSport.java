package com.example.sportmate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserFavoriteSport {
    @Column(nullable = false)
    private Integer levelId;

    @EmbeddedId
    private UserFavorieSportId userSportId;
}
