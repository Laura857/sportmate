package com.example.sportmate.controller;

import com.example.sportmate.service.SportService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class SportController {
    private static final String SPORT = "api/sports";
    private static final String USER_SPORT = "api/user/{id}/sports";

    private final SportService sportService;

    @GetMapping(SPORT)
    private List<String> getAllSports(){
        return sportService.getAllSports();
    }

    @GetMapping(USER_SPORT)
    private List<String> getUserSports(@PathVariable("id") Integer userId){
        return sportService.getUserSports(userId);
    }
}
