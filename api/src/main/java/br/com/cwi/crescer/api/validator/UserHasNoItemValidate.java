package br.com.cwi.crescer.api.validator;

import br.com.cwi.crescer.api.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
public class UserHasNoItemValidate {

    public void validate(User user, Long itemId) {
        boolean availableItem = user.getItems().stream().anyMatch(item -> Objects.equals(item.getId(), itemId));


        if (!availableItem) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Você não possui esse item");
        }
    }
}
