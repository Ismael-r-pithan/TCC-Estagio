package br.com.cwi.crescer.api.service.user;

import br.com.cwi.crescer.api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class ValidateEmailExistsServiceTest {


    @InjectMocks
    private ValidateEmailExistsService validateEmailExistsService;

    @Mock
    private UserRepository usuarioRepository;

    @Test
    public void shouldThrowExceptionWhenEmailExists() {
        String email = "test@example.com";

        Mockito.when(usuarioRepository.existsByEmail(email)).thenReturn(true);

        try {
            validateEmailExistsService.validate(email);
            fail("Expected ResponseStatusException was not thrown");
        } catch (ResponseStatusException ex) {
            assertEquals(HttpStatus.CONFLICT.getReasonPhrase(), ex.getStatus().getReasonPhrase());
            assertEquals("E-mail já cadastrado", ex.getReason());
        }
    }

    @Test
    public void shouldNotThrowExceptionWhenEmailDoesNotExist() {
        String email = "test@example.com";

        Mockito.when(usuarioRepository.existsByEmail(email)).thenReturn(false);

        try {
            validateEmailExistsService.validate(email);
        } catch (ResponseStatusException ex) {
            fail("Unexpected ResponseStatusException was thrown");
        }
    }
}