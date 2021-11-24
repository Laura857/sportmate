package com.example.sportmate.entity;

import org.springframework.data.annotation.Id;

public record Level(@Id Integer id, String label) {
}
