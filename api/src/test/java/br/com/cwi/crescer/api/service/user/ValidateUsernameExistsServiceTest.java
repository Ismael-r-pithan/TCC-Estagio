package br.com.cwi.crescer.api.service.user;

import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ValidateUsernameExistsServiceTest {

    @Mock
    private UserRepository usuarioRepository;

    @InjectMocks
    private ValidateUsernameExistsService service;

    @Test
    @DisplayName("Deve lançar exceção quando o username já está cadastrado")
    public void shouldThrowExceptionWhenUsernameAlreadyExists() {
        // given
        String username = "john_doe";
        User user = new User();

        given(usuarioRepository.existsByUsername(username)).willReturn(true);

        // when
        try {
            service.validate(username);
            fail("Deveria ter lançado exceção");
        } catch (ResponseStatusException ex) {
            // then
            assertThat(ex.getStatus()).isEqualTo(HttpStatus.CONFLICT);
            assertThat(ex.getReason()).isEqualTo("Username já cadastrado");
        }
    }



    @Test
    @DisplayName("Não deve lançar exceção quando username não existe")
    void shouldNotThrowExceptionWhenUsernameDoesNotExist() {
        // given
        String username = "johndoe";
        given(usuarioRepository.existsByUsername(username)).willReturn(false);

        // when
        service.validate(username);

        // then
        verify(usuarioRepository).existsByUsername(username);
    }
}
