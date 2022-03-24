package com.example.sportmate.controller;

import com.example.sportmate.service.LevelService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class LevelController {
    private static final String LEVEL = "api/levels";

    private final LevelService levelService;

    @GetMapping(LEVEL)
    @Operation(summary = "WS qui récupère tous les niveaux")
    private List<String> getAllLevels() {
        return levelService.getAllLevels();
    }
}
