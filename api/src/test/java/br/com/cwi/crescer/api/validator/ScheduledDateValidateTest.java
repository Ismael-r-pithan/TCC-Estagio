package br.com.cwi.crescer.api.validator;

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
public class ScheduledDateValidateTest {

    @InjectMocks
    private ScheduledDateValidate scheduledDateValidate;

    @Test
    @DisplayName("Deve lançar exceção se data programada for anterior ao dia de hoje")
    public void shouldThrowExceptionIfScheduledDateIsBeforeToday() {
        LocalDate scheduledDate = LocalDate.now().minusDays(1);

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
            scheduledDateValidate.validate(scheduledDate);
        });

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        Assertions.assertEquals("Data programada não pode ser menor que hoje.", exception.getReason());
    }

    @Test
    @DisplayName("Não deve lançar exceção se data programada for igual ou posterior ao dia de hoje")
    public void shouldNotThrowExceptionIfScheduledDateIsTodayOrAfter() {
        LocalDate scheduledDate = LocalDate.now();

        LocalDate finalScheduledDate = scheduledDate;
        Assertions.assertDoesNotThrow(() -> {
            scheduledDateValidate.validate(finalScheduledDate);
        });

        scheduledDate = LocalDate.now().plusDays(1);

        LocalDate finalScheduledDate1 = scheduledDate;
        Assertions.assertDoesNotThrow(() -> {
            scheduledDateValidate.validate(finalScheduledDate1);
        });
    }

}
