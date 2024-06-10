package com.example.cielobackend.controller;

import com.example.cielobackend.dto.UserDto;
import com.example.cielobackend.dto.UserDtoResponse;
import com.example.cielobackend.model.User;
import com.example.cielobackend.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.basePath}/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public UserDtoResponse getUserById(@PathVariable long id) {
        return userService.getUser(id);
    }

    @GetMapping
    private Page<UserDtoResponse> getUsers(
            @RequestParam(value = "pageNo", defaultValue = "1") @Min(1) Integer pageNo,
            @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(20) Integer limit,
            @RequestParam(value = "sortBy", defaultValue = "firstName") String sortBy,
            @RequestParam(value = "orderBy", defaultValue = "asc") String orderBy) {
        return userService.getAllUsers(pageNo, limit, sortBy, orderBy);
    }

    @PostMapping
    public UserDtoResponse addUser(@RequestBody @Valid UserDto userDto) {
        return userService.addUser(userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return ResponseEntity
               .noContent()
               .build();
    }
}
