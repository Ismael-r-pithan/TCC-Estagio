package br.com.cwi.crescer.api.service.factories;

import br.com.cwi.crescer.api.domain.User;

public class UserFactory {

    public static User get() {
        User user = new User();
        return user;
    }
}
