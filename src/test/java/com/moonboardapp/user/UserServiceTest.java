package com.moonboardapp.user;

import com.moonboardapp.track.exception.NotFoundException;
import com.moonboardapp.user.dto.UserDto;
import com.moonboardapp.user.mapper.UserMapper;
import com.moonboardapp.user.model.User;
import com.moonboardapp.user.repository.UserRepository;
import com.moonboardapp.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;
    private User user1;
    private User user2;

    @BeforeEach
    void beforeEach() {
        user1 = new User(1L, "UserName1", "UserEmail@mail.ru", "UserCity", 0L);
        user2 = new User(1L, "UserName2", "UserEmail2@mail.ru", "UserCity2", 6L);
    }

    @Test
    void createUserTest() {
        when(userRepository.save(any(User.class))).thenReturn(user1);

        UserDto userDto = userService.createUser(UserMapper.USER_MAPPER.toShortUserDto(user1));

        assertEquals(1, userDto.getUserId());
        assertEquals("UserName1", userDto.getName());
        assertEquals("UserEmail@mail.ru", userDto.getEmail());
        assertEquals("UserCity", userDto.getCity());
        assertEquals(0, userDto.getRate());
    }

    @Test
    void updateUserTest() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user1));

        UserDto userDto = UserMapper.USER_MAPPER.toUserDto(user2);
        userService.updateUser(userDto.getUserId(), userDto);

        assertEquals(1, userDto.getUserId());
        assertEquals("UserName2", userDto.getName());
        assertEquals("UserEmail2@mail.ru", userDto.getEmail());
        assertEquals("UserCity2", userDto.getCity());
        assertEquals(6, userDto.getRate());
    }

    @Test
    void getUserTest() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user1));

        UserDto userDto = UserMapper.USER_MAPPER.toUserDto(user1);
        userService.getUserById(userDto.getUserId());

        assertEquals(1, userDto.getUserId());
        assertEquals("UserName1", userDto.getName());
        assertEquals("UserEmail@mail.ru", userDto.getEmail());
        assertEquals("UserCity", userDto.getCity());
        assertEquals(0, userDto.getRate());
    }

    @Test
    void getUserWithWrongIdTest() {
        when(userRepository.findById(55L)).thenThrow(new NotFoundException("User with id 55 not found"));

        final NotFoundException exception = assertThrows(NotFoundException.class,
                () -> userService.getUserById(55L));

        assertEquals("User with id 55 not found", exception.getMessage());
    }

    @Test
    void deleteUserWithWrongIdTest() {
        when(userRepository.findById(user1.getUserId())).thenReturn(Optional.of(user1));

        userService.deleteUser(user1.getUserId());

        verify(userRepository, times(1)).findById(user1.getUserId());
        verify(userRepository, times(1)).deleteById(user1.getUserId());

        assertFalse(userRepository.existsById(user1.getUserId()));
    }

}
