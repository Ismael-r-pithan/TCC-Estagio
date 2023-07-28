package br.com.cwi.crescer.api.validator;

import br.com.cwi.crescer.api.domain.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class TaskNotFinishedValidateTest {

    @InjectMocks
    private TaskNotFinishedValidate taskNotFinishedValidate;

    @Test
    @DisplayName("Deve lançar exceção quando tarefa já está concluída")
    public void shouldThrowExceptionWhenTaskIsAlreadyFinished() {
        Task task = Task.builder().id(1L).name("Comprar leite").scheduledDate(LocalDate.now()).finishedDate(LocalDate.now()).build();

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
            taskNotFinishedValidate.validate(task);
        });

        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, exception.getStatus());
        Assertions.assertEquals("Tarefa já está concluida.", exception.getReason());
    }

    @Test
    @DisplayName("Não deve lançar exceção quando tarefa não está concluída")
    public void shouldNotThrowExceptionWhenTaskIsNotFinished() {
        Task task = Task.builder().id(1L).name("Comprar leite").scheduledDate(LocalDate.now()).finishedDate(null).build();

        Assertions.assertDoesNotThrow(() -> {
            taskNotFinishedValidate.validate(task);
        });
    }

}
