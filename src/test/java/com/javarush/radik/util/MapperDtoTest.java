package com.javarush.radik.util;

import com.javarush.radik.entity.DTO.UserDto;
import com.javarush.radik.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapperDtoTest {

    //проверка при маппинге из user в userDto что все поля userDto совпадают с user
    @Test
    void getUserDto() {
        User user = new User();
        user.setId(1);
        user.setEmail("someEmail@mail.ru");
        user.setName("name");

        UserDto dto = MapperDto.getUserDto(user);

        assertAll("проверка корректности передачи параметров",
                () -> assertEquals(user.getId(), dto.getId()),
                () -> assertEquals(user.getName(), dto.getName()),
                () -> assertEquals(user.getEmail(), dto.getEmail())
        );
    }
    //проверяем при маппинге из user в userDto в момент когда все поля user пустые то после маппинга userDto
    //так же поля должны быть пустыми
    @Test
    void WhenUserEmpty() {
        User user = new User();
        UserDto dto = MapperDto.getUserDto(user);

        assertAll("проверка корректности передачи параметров когда user пустой",
                () -> assertEquals(user.getId(), dto.getId()),
                () -> assertEquals(user.getName(), dto.getName()),
                () -> assertEquals(user.getEmail(), dto.getEmail())
        );
    }
}