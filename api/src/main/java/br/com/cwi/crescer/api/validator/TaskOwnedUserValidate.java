package br.com.cwi.crescer.api.validator;

import br.com.cwi.crescer.api.domain.Task;
import br.com.cwi.crescer.api.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
public class TaskOwnedUserValidate {
    private static final String MESSAGE_ERROR_NOT_OWNED_USER = "Voce deve ser o autor da tarefa.";

    public void validate(User me, Task task) {
        if (!Objects.equals(me.getId(), task.getAutor().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MESSAGE_ERROR_NOT_OWNED_USER);
        }
    }
}
