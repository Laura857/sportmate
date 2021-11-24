package com.example.sportmate.entity;

import org.springframework.data.annotation.Id;

public record Sport(@Id Integer id, String label) {
}
