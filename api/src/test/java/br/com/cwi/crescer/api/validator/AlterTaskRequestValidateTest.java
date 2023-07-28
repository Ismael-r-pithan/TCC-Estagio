package br.com.cwi.crescer.api.validator;

import br.com.cwi.crescer.api.controller.dtos.request.task.AlterTaskRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.BDDAssertions.catchThrowable;

@ExtendWith(MockitoExtension.class)
public class AlterTaskRequestValidateTest {

    @InjectMocks
    private AlterTaskRequestValidate alterTaskRequestValidate;

    @Test
    @DisplayName("Deve lançar exceção quando todos os campos de alteração são nulos")
    void shouldThrowExceptionWhenAllFieldsAreNull() {
        // given
        AlterTaskRequest request = new AlterTaskRequest();

        // when
        Throwable exception = catchThrowable(() -> alterTaskRequestValidate.validate(request));

        // then
        assertThat(exception).isInstanceOf(ResponseStatusException.class);
        ResponseStatusException responseStatusException = (ResponseStatusException) exception;
        assertThat(responseStatusException.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseStatusException.getReason()).isEqualTo("Deve alterar pelo menos 1 campo.");
    }

    @Test
    @DisplayName("Não deve lançar exceção quando pelo menos 1 campo de alteração não é nulo")
    void shouldNotThrowExceptionWhenAtLeastOneFieldIsNotNull() {
        // given
        AlterTaskRequest request = new AlterTaskRequest();
        request.setName("New title");

        // when
        Throwable exception = catchThrowable(() -> alterTaskRequestValidate.validate(request));

        // then
        assertThat(exception).isNull();
    }
}
