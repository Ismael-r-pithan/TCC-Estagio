package br.com.cwi.crescer.api.validator;

import br.com.cwi.crescer.api.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
public class UserWhoRepliedValidate {
    private static final String MESSAGE_ERROR_USER_NOT_ALLOW= "Voce deve ser o usuario que recebeu a solicitacao de amizade.";

    public void validate(User me, User whoReplied) {
        if (!Objects.equals(me.getId(), whoReplied.getId())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, MESSAGE_ERROR_USER_NOT_ALLOW);
        }
    }
}
