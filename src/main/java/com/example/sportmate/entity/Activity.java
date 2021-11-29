package com.example.sportmate.entity;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;

public record Activity(@Id Integer id,
                       boolean isEvent,
                       String activityName,
                       LocalDate activityDate,
                       Integer creator,
                       String address,
                       String longitude,
                       String latitude,
                       Integer participant,
                       Integer sport,
                       Integer activityLevel,
                       LocalDate created,
                       LocalDate updated) {
}
