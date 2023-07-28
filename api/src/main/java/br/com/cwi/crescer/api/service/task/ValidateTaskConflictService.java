package br.com.cwi.crescer.api.service.task;

import br.com.cwi.crescer.api.domain.Task;
import br.com.cwi.crescer.api.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ValidateTaskConflictService {
    private static final String MESSAGE_ERROR_CONFLICT = "Já existe uma tarefa agendada nesse horario.";

    @Autowired
    private TaskRepository taskRepository;

    public void validate(Task task) {
        boolean isConflict = taskRepository.existsByAutorAndScheduledDateAndStartTimeLessThanEqualAndEndTimeGreaterThanEqualAndFinishedDateIsNull(
                task.getAutor(),
                task.getScheduledDate(),
                task.getStartTime(),
                task.getEndTime());

        if (isConflict) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, MESSAGE_ERROR_CONFLICT);
        }
    }
}
