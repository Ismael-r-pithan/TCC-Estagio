package br.com.cwi.crescer.api.service.task;

import br.com.cwi.crescer.api.domain.Task;
import br.com.cwi.crescer.api.repository.TaskRepository;
import br.com.cwi.crescer.api.validator.TaskNotFinishedValidate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FinishTaskServiceTest {

    @InjectMocks
    private FinishTaskService finishTaskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskNotFinishedValidate taskNotFinishedValidate;

    @Mock
    private GetTaskService getTaskService;

    @Test
    @DisplayName("Deve finalizar uma tarefa")
    void testFinish() {
        // Definir dados de entrada
        Long taskId = 1L;
        Task task = new Task();
        task.setRecurring(false);

        // Configurar o comportamento dos mocks
        when(getTaskService.getOwnedTask(taskId)).thenReturn(task);

        // Executar o método a ser testado
        finishTaskService.finish(taskId);

        // Verificar o resultado
        verify(getTaskService, times(1)).getOwnedTask(taskId);
        verify(taskNotFinishedValidate, times(1)).validate(task);
        verify(taskRepository, times(1)).save(task);
        assertNotNull(task.getFinishedDate());
    }
}
