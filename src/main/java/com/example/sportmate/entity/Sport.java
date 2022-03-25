package com.example.sportmate.entity;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;

public record Sport(
        @Id Integer id,

        @NotBlank(message = "Le label est obligatoire.")
        String label) {
}
