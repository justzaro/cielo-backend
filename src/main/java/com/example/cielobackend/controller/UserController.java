package com.example.cielobackend.controller;

import com.example.cielobackend.dto.UserDto;
import com.example.cielobackend.dto.UserDtoResponse;
import com.example.cielobackend.model.User;
import com.example.cielobackend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.basePath}/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    private Page<UserDtoResponse> getUsers(@RequestParam(value = "page_no", defaultValue = "1") Integer pageNo,
                                           @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                           @RequestParam(value = "order_by", defaultValue = "firstName") String sortBy,
                                           @RequestParam(value = "order_type", defaultValue = "asc") String orderBy) {
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
