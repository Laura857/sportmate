package com.example.sportmate.controller;

import com.example.sportmate.service.LevelService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class LevelController {
    private static final String LEVEL = "api/level";

    private final LevelService levelService;

    @GetMapping(LEVEL)
    private List<String> getAllLevels() {
        return levelService.getAllLevels();
    }
}
