package com.moonboardapp.user.service;

import com.moonboardapp.exception.ForbiddenException;
import com.moonboardapp.exception.NotFoundException;
import com.moonboardapp.user.dto.ShortUserDto;
import com.moonboardapp.user.dto.UserDto;
import com.moonboardapp.user.mapper.UserMapper;
import com.moonboardapp.user.model.User;
import com.moonboardapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserDto createUser(ShortUserDto shortUserDto) {
        User user = userRepository.save(UserMapper.USER_MAPPER.toUserFromShortDto(shortUserDto));
        log.info("User created with id {}", user.getUserId());
        return UserMapper.USER_MAPPER.toUserDto(user);
    }

    @Transactional
    public UserDto updateUser(long userId, UserDto userDto) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id %d not found", userId)));
        if (user.getUserId() != userId) {
            throw new ForbiddenException("Only owner can change user");
        }
        if (!userDto.getName().isEmpty()) {
            user.setName(userDto.getName());
        }
        if (!userDto.getEmail().isEmpty()) {
            user.setEmail(userDto.getEmail());
        }
        if (!userDto.getCity().isEmpty()) {
            user.setCity(userDto.getCity());
        }
        return UserMapper.USER_MAPPER.toUserDto(user);
    }

    @Transactional
    public UserDto updateUserByAdmin(long userId, UserDto userDto) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id %d not found", userId)));
        if (!userDto.getName().isEmpty()) {
            user.setName(userDto.getName());
        }
        if (!userDto.getEmail().isEmpty()) {
            user.setEmail(userDto.getEmail());
        }
        if (!userDto.getCity().isEmpty()) {
            user.setCity(userDto.getCity());
        }
        return UserMapper.USER_MAPPER.toUserDto(user);
    }

    public UserDto getUserById(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id %d not found", userId)));
        return UserMapper.USER_MAPPER.toUserDto(user);
    }

    public void deleteUser(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id %d not found", userId)));
        if (user.getUserId() != userId) {
            throw new ForbiddenException("Only owner can change user");
        }
        userRepository.deleteById(userId);
        log.info("User with id {} deleted", user.getUserId());
    }

    public void deleteUserByAdmin(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id %d not found", userId)));
        userRepository.deleteById(userId);
        log.info("User with id {} deleted", user.getUserId());
    }
}
