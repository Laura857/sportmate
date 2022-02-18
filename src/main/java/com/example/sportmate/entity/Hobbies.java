package com.example.sportmate.entity;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;

public record Hobbies(
        @Id
        Integer id,

        @NotBlank(message = "Le label est obligatoire.")
        String label) {
}
