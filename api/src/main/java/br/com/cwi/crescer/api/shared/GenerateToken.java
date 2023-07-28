package br.com.cwi.crescer.api.shared;


import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;


@Service
public class GenerateToken {
    public static String generate() {
        SecureRandom random = new SecureRandom();
        StringBuilder str = new StringBuilder(new BigInteger(32, random).toString(32));
        while (str.length() < 8) {
            str.insert(0, "0");
        }
        return str.toString();
    }
}
