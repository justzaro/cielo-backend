package com.example.cielobackend.service;

import com.example.cielobackend.dto.UserDto;
import com.example.cielobackend.dto.UserDtoResponse;

import java.util.List;

public interface UserService {
    UserDtoResponse getUser(long id);
    List<UserDtoResponse> getAllUsers();
    UserDtoResponse addUser(UserDto userDto);
    UserDtoResponse updateUser(long id, UserDto userDto);
    void deleteUser(long id);
}
