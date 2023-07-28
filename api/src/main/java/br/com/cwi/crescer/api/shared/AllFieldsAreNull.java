package br.com.cwi.crescer.api.shared;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;


@Service
public class AllFieldsAreNull {

    public static boolean verifyFields(Object obj) {
        return Arrays.stream(obj.getClass().getDeclaredFields())
                .allMatch(field -> {
                    field.setAccessible(true);
                    try {
                        return field.get(obj) == null;
                    } catch (IllegalAccessException e) {
                        return false;
                    }
                });
    }
}
