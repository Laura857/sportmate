package com.example.sportmate.controller;

import com.example.sportmate.service.LevelService;
import com.example.sportmate.service.SportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LevelController {
    private final String LEVEL = "api/level";

    private final LevelService levelService;

    public LevelController(final LevelService levelService) {
        this.levelService = levelService;
    }

    @GetMapping(LEVEL)
    private List<String> getAllLevels() {
        return levelService.getAllLevels();
    }
}
