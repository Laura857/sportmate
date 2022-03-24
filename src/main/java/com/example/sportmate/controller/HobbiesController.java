package com.example.sportmate.controller;

import com.example.sportmate.service.HobbiesService;
import com.example.sportmate.service.LevelService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class HobbiesController {
    private static final String USER_HOBBIES = "api/user/{id}/hobbies";

    private final HobbiesService hobbiesService;

    @GetMapping(USER_HOBBIES)
    private List<String> getUserHobbies(@PathVariable("id") Integer id) {
        return hobbiesService.getUserHobbies(id);
    }
}
