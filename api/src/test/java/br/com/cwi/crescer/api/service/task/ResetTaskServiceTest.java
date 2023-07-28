package br.com.cwi.crescer.api.service.task;

import br.com.cwi.crescer.api.domain.Task;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.repository.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ResetTaskServiceTest {

    @InjectMocks
    private ResetTaskService resetTaskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private GetTaskService getTaskService;

    @Test
    @DisplayName("Deve resetar a data de finalização da tarefa")
    void shouldResetFinishDateOfTask() {
        // given
        Long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);
        task.setFinishedDate(LocalDate.now());

        given(getTaskService.getOwnedTask(taskId)).willReturn(task);

        // when
        resetTaskService.resetFinish(taskId);

        // then
        verify(taskRepository, times(1)).save(task);
        assertNull(task.getFinishedDate());
    }


}
