package br.com.cwi.crescer.api.validator;

import br.com.cwi.crescer.api.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserWhoRepliedValidateTest {

    @InjectMocks
    private UserWhoRepliedValidate tested;

    @Mock
    private User me;

    @Test
    @DisplayName("Deve lançar exceção quando o usuário que respondeu não é o usuário logado")
    void shouldThrowExceptionWhenUserWhoRepliedIsNotCurrentUser() {
        // Mockando os usuários
        User userWhoReplied = new User();
        userWhoReplied.setId(2L);
        when(me.getId()).thenReturn(1L);

        // Validando se a exceção é lançada quando o usuário que respondeu não é o usuário logado
        assertThrows(ResponseStatusException.class, () -> tested.validate(me, userWhoReplied));
    }

    @Test
    @DisplayName("Não deve lançar exceção quando o usuário que respondeu é o usuário logado")
    void shouldNotThrowExceptionWhenUserWhoRepliedIsCurrentUser() {
        // Mockando os usuários
        when(me.getId()).thenReturn(1L);

        // Validando se a exceção não é lançada quando o usuário que respondeu é o usuário logado
        tested.validate(me, me);
    }
}
