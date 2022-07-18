package com.example.sportmate.controller;

import com.example.sportmate.record.hobbies.UpdateUserHobbiesDto;
import com.example.sportmate.service.HobbiesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class HobbiesController {
    private static final String USER_HOBBIES = "api/user/{id}/hobbies";

    private final HobbiesService hobbiesService;

    @GetMapping(USER_HOBBIES)
    @Operation(summary = "WS qui récupère tous les hobbies d'un utilisateur")
    public List<String> getUserHobbies(@Schema(example = "1") @PathVariable("id") final Integer userId) {
        return hobbiesService.getUserHobbies(userId);
    }

    @PutMapping(USER_HOBBIES)
    @Operation(summary = "WS qui met à jours les hobbies d'un utilisateur")
    public void updateUserHobbies(@Schema(example = "1") @PathVariable("id") final Integer userId,
                                  @Valid @RequestBody final UpdateUserHobbiesDto updateUserHobbiesDto) {
        hobbiesService.updateUserHobbies(userId, updateUserHobbiesDto);
    }
}
