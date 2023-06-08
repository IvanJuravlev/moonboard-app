package com.moonboardapp.user.mapper;

import com.moonboardapp.user.dto.ShortUserDto;
import com.moonboardapp.user.dto.UserDto;
import com.moonboardapp.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    UserDto toUserDto(User user);
    User toUser(UserDto userDto);

    ShortUserDto toShortUserDto(User user);
    User toUserFromShortDto(ShortUserDto shortUserDto);

}
