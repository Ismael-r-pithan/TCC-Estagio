package br.com.cwi.crescer.api.validator;

import br.com.cwi.crescer.api.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
public class SelfFriendshipValidate {
    private static final String MESSAGE_ERROR_SELF_FRIENDSHIP = "O usuário não pode ser amigo de si mesmo";

    public void validate(User me, User amigo) {
        if (Objects.equals(me.getId(), amigo.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MESSAGE_ERROR_SELF_FRIENDSHIP);
        }
    }
}
