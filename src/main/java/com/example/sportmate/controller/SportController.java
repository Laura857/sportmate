package com.example.sportmate.controller;

import com.example.sportmate.record.authentification.signing.SportDto;
import com.example.sportmate.service.SportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Operation(summary = "WS qui récupère tous les sports")
    public List<String> getAllSports() {
        return sportService.getAllSports();
    }

    @GetMapping(USER_SPORT)
    @Operation(summary = "WS qui récupère tous les sports d'un utilisateur")
    public List<SportDto> getUserSports(@Schema(example = "1") @PathVariable("id") final Integer userId) {
        return sportService.getUserSports(userId);
    }
}
