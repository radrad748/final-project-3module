package com.javarush.radik.util;

import com.javarush.radik.controllers.FirstPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;

public class Encoder {

    public static String encode(String str) {
       byte[] encodedBytes = Base64.getEncoder().encode(str.getBytes());
       return new String(encodedBytes);
    }

    public static String decode(String str) {
        byte[] decodedBytes = Base64.getDecoder().decode(str);
        return new String(decodedBytes);
    }

}
