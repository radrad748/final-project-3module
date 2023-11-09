package com.javarush.radik.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EncoderTest {

    @ParameterizedTest
    @MethodSource("argsFactory")
    void testEncode(String str) {
        String encode = Encoder.encode(str);

        assertNotNull(encode);
        assertNotEquals(str, encode);
    }
    @ParameterizedTest
    @MethodSource("argsFactory")
    void testDecode(String str) {
        String encode = Encoder.encode(str);
        String decode = Encoder.decode(encode);

        assertEquals(str, decode);
    }
    @Test
     void testEncodeWithEmptyInput() {
        String input = "";
        String encoded = Encoder.encode(input);

        assertEquals("", encoded);
    }
    @Test
    void testDecodeWithEmptyInput() {
        String input = "";
        String decoded = Encoder.decode(input);

        assertEquals("", decoded);
    }

    @Test
    void whenArgIsNullEncode() {
        String input = null;
        assertThrows(NullPointerException.class, () -> {
            Encoder.encode(input);
        });
    }
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