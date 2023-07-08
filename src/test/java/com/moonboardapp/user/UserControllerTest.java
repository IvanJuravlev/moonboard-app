package com.moonboardapp.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moonboardapp.user.controller.UserController;
import com.moonboardapp.user.dto.ShortUserDto;
import com.moonboardapp.user.dto.UserDto;
import com.moonboardapp.user.mapper.UserMapper;
import com.moonboardapp.user.model.User;
import com.moonboardapp.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    private UserDto userDto;
    private ShortUserDto shortUserDto;
    @Autowired
    ObjectMapper mapper;
    @MockBean
    UserService userService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        userDto = UserMapper.USER_MAPPER.toUserDto(
                new User(1L, "UserName1", "UserEmail@mail.ru", "UserCity", 5L));
        shortUserDto = UserMapper.USER_MAPPER.toShortUserDto(
                new User(1L, "UserName1", "UserEmail@mail.ru", "UserCity", 5L));
    }

    @Test
    void createUserTest() throws Exception {
        when(userService.createUser(any(ShortUserDto.class))).thenReturn(userDto);

        mockMvc.perform(post("/users")
                .content(mapper.writeValueAsString(shortUserDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(shortUserDto)));
    }

    @Test
    void updateUserTest() throws Exception {
        when(userService.updateUser(anyLong(), any(UserDto.class))).thenReturn(userDto);

        mockMvc.perform(patch("/users/1")
                .content(mapper.writeValueAsString(userDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(userDto)));
    }

    @Test
    void getUserByIdTest() throws Exception {
        when(userService.getUserById(anyLong())).thenReturn(userDto);

        mockMvc.perform(get("/users/1")
                .content(mapper.writeValueAsString(userDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(userDto)));
    }

    @Test
    void deleteUserByIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/1"))
                .andExpect(status().isNoContent());
    }


}
