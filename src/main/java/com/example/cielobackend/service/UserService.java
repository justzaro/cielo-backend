package com.example.cielobackend.service;

import com.example.cielobackend.dto.UserDto;
import com.example.cielobackend.dto.UserDtoResponse;
import org.springframework.data.domain.Page;

public interface UserService {
    UserDtoResponse getUser(long id);
    Page<UserDtoResponse> getAllUsers(Integer pageNo, Integer limit,
                                      String sortBy, String orderBy);
    UserDtoResponse addUser(UserDto userDto);
    UserDtoResponse updateUser(long id, UserDto userDto);
    void setProfilePictureName(long id, String name);
    void deleteUser(long id);
}
