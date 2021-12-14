package com.example.sportmate.controller;

import com.example.sportmate.service.SportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SportController {
    private final String SPORT = "api/sport";

    private final SportService sportService;

    public SportController(final SportService sportService) {
        this.sportService = sportService;
    }

    @GetMapping(SPORT)
    private List<String> getAllSports(){
        return sportService.getAllSports();
    }
}
