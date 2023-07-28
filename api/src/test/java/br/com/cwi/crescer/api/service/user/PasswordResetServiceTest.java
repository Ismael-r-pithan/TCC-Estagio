package br.com.cwi.crescer.api.service.user;

import br.com.cwi.crescer.api.domain.PasswordResetToken;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.repository.PasswordResetTokenRepository;
import br.com.cwi.crescer.api.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PasswordResetServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Mock
    private SendPasswordResetEmailService sendPasswordResetEmailService;

    @InjectMocks
    private PasswordResetService passwordResetService;

    @Test
    @DisplayName("Deve solicitar reset de senha para usuário existente")
    void deveSolicitarResetSenhaParaUsuarioExistente() {
        String email = "usuario@teste.com";
        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        ArgumentCaptor<PasswordResetToken> captor = ArgumentCaptor.forClass(PasswordResetToken.class);

        // when
        passwordResetService.requestPasswordReset(email);

        // then
        verify(userRepository).findByEmail(email);
        verify(passwordResetTokenRepository).save(captor.capture());
        verify(sendPasswordResetEmailService).sendPasswordResetEmail(eq(email), eq(captor.getValue().getToken()));

        PasswordResetToken savedToken = captor.getValue();
        assertNotNull(savedToken);
        assertEquals(user, savedToken.getUser());
        assertNotNull(savedToken.getToken());
        assertTrue(savedToken.getExpiresIn().isAfter(LocalDateTime.now()));
    }

    @Test
    @DisplayName("Deve lançar exceção quando tentar solicitar reset de senha para usuário inexistente")
    void deveLancarExcecaoQuandoSolicitarResetSenhaParaUsuarioInexistente() {

        String email = "usuario@teste.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());


        assertThrows(ResponseStatusException.class, () -> passwordResetService.requestPasswordReset(email));

        verify(userRepository).findByEmail(email);
    }
}
