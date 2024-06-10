package com.example.cielobackend.service.implementation;

import com.example.cielobackend.dto.UserDto;
import com.example.cielobackend.dto.UserDtoResponse;
import com.example.cielobackend.exception.DuplicateUniqueFieldException;
import com.example.cielobackend.exception.ResourceDoesNotExistException;
import com.example.cielobackend.model.User;
import com.example.cielobackend.repository.UserRepository;
import com.example.cielobackend.service.UserService;
import com.example.cielobackend.util.PaginationUtils;
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
        System.out.println(user.getFavouriteListings().size());
        System.out.println(user.getId());
        return modelMapper.map(user, UserDtoResponse.class);
    }

    @Override
    public Page<UserDtoResponse> getAllUsers(Integer pageNo, Integer limit,
                                             String sortBy, String orderBy) {
        Pageable pageable = PaginationUtils.createPageable(pageNo, limit, sortBy, orderBy);

        Page<UserDtoResponse> resultPage = userRepository
                .findAll(pageable)
                .map(user -> modelMapper.map(user, UserDtoResponse.class));

        return resultPage.getContent().size() > 0 ? resultPage : Page.empty();
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

    @Override
    public void setProfilePictureName(long id, String name) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException(USER_DOES_NOT_EXIST));
        user.setProfilePictureName(name);
        userRepository.save(user);
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
