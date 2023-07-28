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
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ValidateTaskConflictServiceTest {
    @InjectMocks
    private ValidateTaskConflictService validateTaskConflictService;

    @Mock
    private TaskRepository taskRepository;

    @Test
    @DisplayName("Deve lançar exceção caso exista conflito de horário")
    void deveLancarExcecaoCasoExistaConflito() {
        // given
        User user = new User();
        LocalDate scheduledDate = LocalDate.now();
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(10, 0);
        Task task = new Task();
        task.setAutor(user);
        task.setScheduledDate(scheduledDate);
        task.setStartTime(startTime);
        task.setEndTime(endTime);

        when(taskRepository.existsByAutorAndScheduledDateAndStartTimeLessThanEqualAndEndTimeGreaterThanEqualAndFinishedDateIsNull(user, scheduledDate, startTime, endTime))
                .thenReturn(true);

        // when/then
        assertThrows(ResponseStatusException.class,
                () -> validateTaskConflictService.validate(task),
                "Deve lançar exceção quando houver conflito de horário");
    }

    @Test
    @DisplayName("Não deve lançar exceção caso não haja conflito de horário")
    void naoDeveLancarExcecaoCasoNaoHajaConflito() {
        // given
        User user = new User();
        LocalDate scheduledDate = LocalDate.now();
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(10, 0);
        Task task = new Task();
        task.setAutor(user);
        task.setScheduledDate(scheduledDate);
        task.setStartTime(startTime);
        task.setEndTime(endTime);

        when(taskRepository.existsByAutorAndScheduledDateAndStartTimeLessThanEqualAndEndTimeGreaterThanEqualAndFinishedDateIsNull(user, scheduledDate, startTime, endTime))
                .thenReturn(false);

        // when/then
        assertDoesNotThrow(() -> validateTaskConflictService.validate(task),
                "Não deve lançar exceção quando não houver conflito de horário");
    }
}
