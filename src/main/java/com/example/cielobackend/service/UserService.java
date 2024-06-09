package com.example.cielobackend.service;

import com.example.cielobackend.dto.UserDto;
import com.example.cielobackend.dto.UserDtoResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    UserDtoResponse getUser(long id);
    Page<UserDtoResponse> getAllUsers(Integer pageNo, Integer limit,
                                      String sortBy, String orderBy);
    UserDtoResponse addUser(UserDto userDto);
    UserDtoResponse updateUser(long id, UserDto userDto);
    void deleteUser(long id);
}
