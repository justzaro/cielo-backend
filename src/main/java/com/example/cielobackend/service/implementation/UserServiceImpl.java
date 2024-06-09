package com.example.cielobackend.service.implementation;

import com.example.cielobackend.dto.UserDto;
import com.example.cielobackend.dto.UserDtoResponse;
import com.example.cielobackend.exception.DuplicateUniqueFieldException;
import com.example.cielobackend.exception.ResourceDoesNotExistException;
import com.example.cielobackend.model.User;
import com.example.cielobackend.repository.UserRepository;
import com.example.cielobackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.cielobackend.common.ExceptionMessages.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDtoResponse getUser(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException(USER_DOES_NOT_EXIST));
        return modelMapper.map(user, UserDtoResponse.class);
    }

    private Sort getSortOrder(String sortBy, String orderBy) {
        if ("asc".equalsIgnoreCase(orderBy)) {
            return Sort.by(sortBy).ascending();
        } else if ("desc".equalsIgnoreCase(orderBy)) {
            return Sort.by(sortBy).descending();
        } else {
            throw new RuntimeException("Invalid Input in order by!!!");
        }
    }

    @Override
    public Page<UserDtoResponse> getAllUsers(Integer pageNo, Integer limit,
                                             String sortBy, String orderBy) {
        Sort sortingOrder = getSortOrder(sortBy, orderBy);
        Pageable pageable = PageRequest.of(pageNo - 1, limit, sortingOrder);
        Page<UserDtoResponse> result = userRepository
                .findAll(pageable)
                .map(user -> modelMapper.map(user, UserDtoResponse.class));

        if (result.getContent().size() > 0) {
            return result;
        }
        return Page.empty();
    }

    @Override
    public UserDtoResponse addUser(UserDto userDto) {
        validateUserDto(userDto);
        User user = modelMapper.map(userDto, User.class);

        userRepository.save(user);
        return modelMapper.map(user, UserDtoResponse.class);
    }

    @Override
    public UserDtoResponse updateUser(long id, UserDto userDto) {
        return null;
    }

    @Override
    public void deleteUser(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException(USER_DOES_NOT_EXIST));
        userRepository.delete(user);
    }

    private void validateUserDto(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new DuplicateUniqueFieldException(DUPLICATE_EMAIL);
        }

        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new DuplicateUniqueFieldException(DUPLICATE_USERNAME);
        }
    }
}
