package com.javarush.radik.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EncoderTest {

    //проверка что входная строка не равна закодированой строке
    @ParameterizedTest
    @MethodSource("argsFactory")
    void testEncode(String str) {
        String encode = Encoder.encode(str);

        assertNotNull(encode);
        assertNotEquals(str, encode);
    }
    //кодируем входную строку, после декодируем и проверяем что входная строка равна декодированой строке
    @ParameterizedTest
    @MethodSource("argsFactory")
    void testDecode(String str) {
        String encode = Encoder.encode(str);
        String decode = Encoder.decode(encode);

        assertEquals(str, decode);
    }
    //проверка при кодировании пустую строку
    @Test
     void testEncodeWithEmptyInput() {
        String input = "";
        String encoded = Encoder.encode(input);

        assertEquals("", encoded);
    }
    //проверка при декодировании пустую строку
    @Test
    void testDecodeWithEmptyInput() {
        String input = "";
        String decoded = Encoder.decode(input);

        assertEquals("", decoded);
    }
    //при кодировке null выкидывает NullPointerException
    @Test
    void whenArgIsNullEncode() {
        String input = null;
        assertThrows(NullPointerException.class, () -> {
            Encoder.encode(input);
        });
    }
    //при декодировке null выкидывает NullPointerException
    @Test
    void whenArgIsNullDecode() {
        String input = null;
        assertThrows(NullPointerException.class, () -> {
            Encoder.decode(input);
        });
    }

    static Stream<String> argsFactory() {
        return Stream.of("one", "two",  "three");
    }
}