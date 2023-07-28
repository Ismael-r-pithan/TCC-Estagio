package br.com.cwi.crescer.api.service.task;

import br.com.cwi.crescer.api.controller.dtos.response.TaskResponse;
import br.com.cwi.crescer.api.domain.Task;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.mapper.TaskMapper;
import br.com.cwi.crescer.api.repository.TaskRepository;
import br.com.cwi.crescer.api.service.user.GetUserAuthenticatedService;
import br.com.cwi.crescer.api.validator.TaskOwnedUserValidate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class GetTaskServiceTest {

    @InjectMocks
    private GetTaskService getTaskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private GetUserAuthenticatedService getUserAuthenticatedService;

    @Mock
    private TaskOwnedUserValidate taskOwnedUserValidate;

    @Test
    @DisplayName("Deve retornar a tarefa correta")
    public void shouldReturnCorrectTask() {
        // given
        Long taskId = 1L;
        User user = new User();
        Task task = new Task();
        task.setId(taskId);

        given(getUserAuthenticatedService.get()).willReturn(user);
        given(taskRepository.findById(taskId)).willReturn(Optional.of(task));

        TaskResponse taskResponse = new TaskResponse();
        given(taskMapper.toResponse(task)).willReturn(taskResponse);

        // when
        TaskResponse result = getTaskService.getResponse(taskId);

        // then
        assertThat(result).isSameAs(taskResponse);
    }

    @Test
    @DisplayName("Deve lançar exceção quando a tarefa não é encontrada")
    public void shouldThrowExceptionWhenTaskNotFound() {
        // given
        Long taskId = 1L;

        given(taskRepository.findById(taskId)).willReturn(Optional.empty());

        // when
        Throwable exception = catchThrowable(() -> getTaskService.getOwnedTask(taskId));

        // then
        assertThat(exception).isInstanceOf(ResponseStatusException.class);
        ResponseStatusException responseStatusException = (ResponseStatusException) exception;
        assertThat(responseStatusException.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseStatusException.getReason()).isEqualTo("Tarefa não encontrada");
    }



}
