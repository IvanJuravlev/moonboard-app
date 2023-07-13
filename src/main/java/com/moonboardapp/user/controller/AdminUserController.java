package com.moonboardapp.user.controller;

import com.moonboardapp.user.dto.UserDto;
import com.moonboardapp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/users/admin")
@RequiredArgsConstructor
public class AdminUserController {
    private final UserService userService;

    @PatchMapping("{userId}")
    public UserDto updateUser(@PathVariable long userId, @RequestBody UserDto userDto) {
        return userService.updateUserByAdmin(userId, userDto);
    }

    @DeleteMapping("{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable long userId) {
        userService.deleteUserByAdmin(userId);
    }
}
