package br.com.cwi.crescer.api.service.user;

import java.time.LocalDateTime;
import java.util.Optional;

import br.com.cwi.crescer.api.domain.PasswordResetToken;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.repository.PasswordResetTokenRepository;
import br.com.cwi.crescer.api.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ResetPasswordServiceTest {

    @InjectMocks
    private ResetPasswordService resetPasswordService;

    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Mock
    private UserRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("Deve redefinir a senha com sucesso")
    void deveRedefinirSenhaComSucesso() {
        String token = "token123";
        String newPassword = "novaSenha123";
        User user = new User();
        user.setId(1L);
        user.setPassword("senhaAntiga123");

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user);
        passwordResetToken.setExpiresIn(LocalDateTime.now().plusDays(1L));

        when(passwordResetTokenRepository.findByToken(token)).thenReturn(Optional.of(passwordResetToken));
        when(passwordEncoder.encode(newPassword)).thenReturn("novaSenha123Encoded");
        when(usuarioRepository.save(user)).thenReturn(user);

        // when
        resetPasswordService.resetPassword(token, newPassword);

        // then
        verify(passwordResetTokenRepository).delete(passwordResetToken);
        assertEquals("novaSenha123Encoded", user.getPassword());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o token não é encontrado")
    void deveLancarExcecaoQuandoTokenNaoEEncontrado() {
        String token = "token123";
        String newPassword = "novaSenha123";

        when(passwordResetTokenRepository.findByToken(token)).thenReturn(Optional.empty());

        // when
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            resetPasswordService.resetPassword(token, newPassword);
        });

        // then
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Token não foi encontrado", exception.getReason());
        verifyNoMoreInteractions(passwordEncoder, usuarioRepository, passwordResetTokenRepository);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o token expirou")
    void deveLancarExcecaoQuandoTokenExpirou() {
        String token = "token123";
        String newPassword = "novaSenha123";

        User user = new User();
        user.setId(1L);
        user.setPassword("senhaAntiga123");

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user);
        passwordResetToken.setExpiresIn(LocalDateTime.now().minusDays(1L));

        when(passwordResetTokenRepository.findByToken(token)).thenReturn(Optional.of(passwordResetToken));

        // when
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            resetPasswordService.resetPassword(token, newPassword);
        });

        // then
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Token expirou", exception.getReason());
        verifyNoMoreInteractions(passwordEncoder, usuarioRepository, passwordResetTokenRepository);
    }

}
