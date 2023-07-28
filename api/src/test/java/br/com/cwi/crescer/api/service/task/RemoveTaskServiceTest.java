package br.com.cwi.crescer.api.service.task;

import br.com.cwi.crescer.api.domain.Task;
import br.com.cwi.crescer.api.repository.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RemoveTaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private GetTaskService getTaskService;

    @InjectMocks
    private RemoveTaskService removeTaskService;

    @Test
    @DisplayName("Deve remover a tarefa quando encontrada")
    void shouldRemoveTaskWhenFound() {
        // given
        Long taskId = 1L;
        Task task = new Task();
        given(getTaskService.getOwnedTask(taskId)).willReturn(task);

        // when
        removeTaskService.remove(taskId);

        // then
        verify(taskRepository, times(1)).delete(task);
    }

    @Test
    @DisplayName("Deve lançar exceção quando a tarefa não for encontrada")
    void shouldThrowExceptionWhenTaskNotFound() {
        // given
        Long taskId = 1L;
        given(getTaskService.getOwnedTask(taskId)).willThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        // when
        Throwable exception = catchThrowable(() -> removeTaskService.remove(taskId));

        // then
        assertThat(exception).isInstanceOf(ResponseStatusException.class);
        ResponseStatusException responseStatusException = (ResponseStatusException) exception;
        assertThat(responseStatusException.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(taskRepository, never()).delete(any(Task.class));
    }
}
