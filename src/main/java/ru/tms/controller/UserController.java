package ru.tms.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tms.dto.BaseResponse;
import ru.tms.dto.user.CreateUserDto;
import ru.tms.dto.user.UserDto;
import ru.tms.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<UserDto>> createUser(@Valid @RequestBody CreateUserDto user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<UserDto>> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BaseResponse<UserDto>> getUserByEmail(@RequestBody UserDto user) {
        return new ResponseEntity<>(userService.getUserByEmail(user), HttpStatus.OK);
    }

}
