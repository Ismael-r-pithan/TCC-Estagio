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

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class GetUserAuthenticatedServiceTest {

    @InjectMocks
    private GetUserAuthenticatedService getUserAuthenticatedService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GetIdUserAuthenticatedService getIdUserAuthenticatedService;

    @Test
    @DisplayName("Deve retornar o usuário autenticado")
    void shouldReturnAuthenticatedUser() {
        // given
        User user = new User();
        Long id = 1L;

        given(getIdUserAuthenticatedService.getId()).willReturn(id);
        given(userRepository.findById(id)).willReturn(Optional.of(user));

        // when
        User result = getUserAuthenticatedService.get();

        // then
        assertThat(result).isEqualTo(user);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o usuário não existe ou não está autenticado")
    void shouldThrowExceptionWhenUserDoesNotExistOrNotAuthenticated() {
        // given
        Long id = 1L;

        given(getIdUserAuthenticatedService.getId()).willReturn(id);
        given(userRepository.findById(id)).willReturn(Optional.empty());

        // when
        Throwable exception = catchThrowable(() -> getUserAuthenticatedService.get());

        // then
        assertThat(exception).isInstanceOf(ResponseStatusException.class);
        ResponseStatusException responseStatusException = (ResponseStatusException) exception;
        assertThat(responseStatusException.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
