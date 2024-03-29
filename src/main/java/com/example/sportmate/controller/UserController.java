package com.example.sportmate.controller;

import com.example.sportmate.record.user.UpdatePasswordRequestDto;
import com.example.sportmate.record.user.UserDataDto;
import com.example.sportmate.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class UserController {
    private static final String USER = "/api/user";
    private static final String USER_ID = USER + "/{id}";
    private static final String USER_ID_UPDATE_PASSWORD = USER_ID + "/updatePassword";

    private final UserService userService;

    @GetMapping(USER_ID)
    @Operation(summary = "WS qui récupère les informations d'un utilisateur")
    public UserDataDto getUser(@Schema(example = "1") @PathVariable("id") final Integer userId) {
        return userService.getUser(userId);
    }

    @PutMapping(USER_ID)
    @Operation(summary = "WS qui met à jour les données peronnelles d'un utilisateur")
    public UserDataDto updateUser(@Schema(example = "1") @PathVariable("id") final Integer userId,
                                  @Valid @RequestBody final UserDataDto userRequest) {
        return userService.updateUser(userId, userRequest);
    }

    @DeleteMapping(USER_ID)
    @Operation(summary = "WS qui met à supprime un utilisateur et toutes ses données")
    public void deleteUser(@Schema(example = "1") @PathVariable("id") final Integer userId) {
        userService.deleteUser(userId);
    }

    @PutMapping(USER_ID_UPDATE_PASSWORD)
    @Operation(summary = "WS qui met à jour le mot de passe d'un utilisateur")
    public void updatePassword(@Schema(example = "1") @PathVariable("id") final Integer userId,
                               @Valid @RequestBody final UpdatePasswordRequestDto updatePasswordRequestDto) {
        userService.updatePassword(userId, updatePasswordRequestDto);
    }
}
