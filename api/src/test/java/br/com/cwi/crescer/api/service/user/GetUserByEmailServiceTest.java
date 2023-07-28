package br.com.cwi.crescer.api.service.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;
import static org.mockito.Mockito.mock;


@ExtendWith(MockitoExtension.class)
public class GetUserByEmailServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUserByEmailService getUserByEmailService;

    @Test
    @DisplayName("Deve retornar um usuário quando encontrado pelo email")
    void deveRetornarUsuarioQuandoEncontradoPeloEmail() {
        User user = mock(User.class);
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));

        User result = getUserByEmailService.get("user@example.com");

        assertEquals(user, result);
    }

    @Test
    @DisplayName("Deve lançar uma exceção quando o usuário não for encontrado pelo email")
    void deveLancarExcecaoQuandoUsuarioNaoEncontradoPeloEmail() {
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            getUserByEmailService.get("user@example.com");
        });
    }
}
