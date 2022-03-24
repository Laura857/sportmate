package com.example.sportmate.controller;

import com.example.sportmate.record.user.UserDataDto;
import com.example.sportmate.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class UserController {
    private static final String USER = "/api/user";
    private static final String USER_ID = USER + "/{id}";

    private final UserService userService;

    @GetMapping(USER_ID)
    private UserDataDto getUser(@PathVariable("id") final Integer userId) {
        return userService.getUser(userId);
    }

    @PutMapping(USER_ID)
    private UserDataDto updateUser(@PathVariable("id") final Integer userId,
                                   @Valid @RequestBody final UserDataDto userRequest) {
        return userService.updateUser(userId, userRequest);
    }

    @PatchMapping(USER_ID)
    private void updatePassword(@PathVariable("id") final Integer userId,
                                @RequestParam("password") final String password) {
        userService.updatePassword(userId, password);
    }

    @DeleteMapping(USER_ID)
    private void deleteUser(@PathVariable("id") final Integer userId) {
        userService.deleteUser(userId);
    }
}
