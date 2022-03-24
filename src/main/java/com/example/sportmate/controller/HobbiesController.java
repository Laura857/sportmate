package com.example.sportmate.controller;

import com.example.sportmate.service.HobbiesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Operation(summary = "WS qui récupère tous les hobbies d'un utilisateur")
    private List<String> getUserHobbies(@Schema(example = "1") @PathVariable("id") final Integer id) {
        return hobbiesService.getUserHobbies(id);
    }
}
