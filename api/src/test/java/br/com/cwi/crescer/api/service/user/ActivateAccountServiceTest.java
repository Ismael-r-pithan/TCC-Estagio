package br.com.cwi.crescer.api.service.user;

import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ActivateAccountServiceTest {

    @InjectMocks
    private ActivateAccountService activateAccountService;

    @Mock
    private GetUserByEmailService getUserByEmailService;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("Deve ativar conta do usuário com token válido")
    void deveAtivarContaDoUsuarioComTokenValido() {
        // Given
        String token = "token-valido";
        String email = "usuario@teste.com";
        User usuario = new User();
        usuario.setEmail(email);
        usuario.setConfirmToken(token);

        // When
        Mockito.when(getUserByEmailService.get(email)).thenReturn(usuario);

        // Then
        assertDoesNotThrow(() -> activateAccountService.activate(token, email));
        assertTrue(usuario.getAtivo());
        Mockito.verify(userRepository, Mockito.times(1)).save(usuario);
    }

    @Test
    @DisplayName("Deve lançar exceção para token inválido")
    void deveLancarExcecaoParaTokenInvalido() {
        // Given
        String token = "token-invalido";
        String email = "usuario@teste.com";
        User usuario = new User();
        usuario.setAtivo(false);
        usuario.setEmail(email);
        usuario.setConfirmToken("token-valido");

        // When
        Mockito.when(getUserByEmailService.get(email)).thenReturn(usuario);

        // Then
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> activateAccountService.activate(token, email));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Token inválido para o usuário", exception.getReason());
        assertFalse(usuario.getAtivo());
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any(User.class));
    }

}
