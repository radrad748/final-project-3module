package com.javarush.radik.util;

import com.javarush.radik.entity.DTO.UserDto;
import com.javarush.radik.entity.User;

public class MapperDto {

    public static UserDto getUserDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

}
