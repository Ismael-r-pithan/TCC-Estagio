package br.com.cwi.crescer.api.service.task;

import br.com.cwi.crescer.api.controller.dtos.request.task.CreateTaskRequest;
import br.com.cwi.crescer.api.controller.dtos.response.TaskResponse;
import br.com.cwi.crescer.api.domain.Task;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.mapper.TaskMapper;
import br.com.cwi.crescer.api.repository.TaskRepository;
import br.com.cwi.crescer.api.service.user.GetUserAuthenticatedService;
import br.com.cwi.crescer.api.validator.ScheduledDateValidate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateTaskServiceTest {

    @InjectMocks
    private CreateTaskService createTaskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private GetUserAuthenticatedService getUserAuthenticatedService;

    @Mock
    private ValidateTaskConflictService validateTaskConflictService;

    @Mock
    private ScheduledDateValidate scheduledDateValidate;

    @Test
    @DisplayName("Deve criar uma nova tarefa")
    void testCreate() {
        // Definir dados de entrada
        CreateTaskRequest request = new CreateTaskRequest();
        Task task = new Task();
        TaskResponse response = new TaskResponse();

        // Configurar o comportamento dos mocks
        when(getUserAuthenticatedService.get()).thenReturn(new User());
        when(taskMapper.toEntity(request)).thenReturn(task);
        when(taskMapper.toResponse(task)).thenReturn(response);

        // Executar o método a ser testado
        TaskResponse result = createTaskService.create(request);

        // Verificar o resultado
        verify(getUserAuthenticatedService, times(1)).get();
        verify(taskMapper, times(1)).toEntity(request);
        verify(validateTaskConflictService, times(1)).validate(task);
        verify(scheduledDateValidate, times(1)).validate(task.getScheduledDate());
        verify(taskRepository, times(1)).save(task);
        verify(taskMapper, times(1)).toResponse(task);
        assertEquals(response, result);
    }
}
