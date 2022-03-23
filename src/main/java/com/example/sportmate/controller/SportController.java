package com.example.sportmate.controller;

import com.example.sportmate.service.SportService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class SportController {
    private static final String SPORT = "api/sport";

    private final SportService sportService;

    @GetMapping(SPORT)
    private List<String> getAllSports(){
        return sportService.getAllSports();
    }
}
