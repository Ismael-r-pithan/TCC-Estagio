package br.com.cwi.crescer.api.validator;

import br.com.cwi.crescer.api.domain.Task;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
public class TaskNotFinishedValidate {
    private static final String MESSAGE_ERROR_FINISHED = "Tarefa já está concluida.";

    public void validate(Task task) {
        if (Objects.nonNull(task.getFinishedDate())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, MESSAGE_ERROR_FINISHED);
        }
    }
}
