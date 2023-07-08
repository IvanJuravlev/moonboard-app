package com.moonboardapp.user.dto;

import com.sun.istack.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    long userId;
    @NotNull
    String name;
    @NotNull
    @Email
    String email;
    @NotNull
    String city;
    long rate;
}
