package br.com.cwi.crescer.api.service.task;

import br.com.cwi.crescer.api.controller.dtos.request.task.AlterTaskRequest;
import br.com.cwi.crescer.api.domain.Task;
import br.com.cwi.crescer.api.repository.TaskRepository;
import br.com.cwi.crescer.api.validator.AlterTaskRequestValidate;
import br.com.cwi.crescer.api.validator.ScheduledDateValidate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class AlterTaskServiceTest {

    @InjectMocks
    private AlterTaskService tested;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private GetTaskService getTaskService;

    @Mock
    private AlterTaskRequestValidate alterTaskRequestValidate;

    @Mock
    private ScheduledDateValidate scheduledDateValidate;

    @Test
    @DisplayName("Deve lançar exceção quando a validação da requisição falhar")
    void shouldThrowExceptionWhenRequestValidationFails() {
        // Given
        Long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);
        AlterTaskRequest request = new AlterTaskRequest();
        given(getTaskService.getOwnedTask(taskId)).willReturn(task);
        willThrow(ResponseStatusException.class).given(alterTaskRequestValidate).validate(request);

        // When/Then
        assertThrows(ResponseStatusException.class, () -> tested.alter(taskId, request));

        verify(getTaskService).getOwnedTask(taskId);
        verify(alterTaskRequestValidate).validate(request);
        verifyNoMoreInteractions(scheduledDateValidate, taskRepository);
    }


    @Test
    @DisplayName("Deve alterar a tarefa quando a requisição for válida")
    void shouldAlterTaskWhenRequestIsValid() {
        // Given
        Long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);
        AlterTaskRequest request = new AlterTaskRequest();
        request.setName("Nova tarefa");
        given(getTaskService.getOwnedTask(taskId)).willReturn(task);

        // When
        tested.alter(taskId, request);

        // Then
        verify(getTaskService).getOwnedTask(taskId);
        verify(alterTaskRequestValidate).validate(request);
        verify(scheduledDateValidate).validate(task.getScheduledDate());
        verify(taskRepository).save(task);
        verifyNoMoreInteractions(scheduledDateValidate, taskRepository);
    }

    @Test
    @DisplayName("Deve alterar a tarefa")
    void shouldAlterTask() {
        // Given
        Long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);
        AlterTaskRequest request = new AlterTaskRequest();
        request.setName("Nova Tarefa");
        given(getTaskService.getOwnedTask(taskId)).willReturn(task);

        // When
        tested.alter(taskId, request);

        // Then
        verify(getTaskService).getOwnedTask(taskId);
        verify(taskRepository).save(task);
    }



}
