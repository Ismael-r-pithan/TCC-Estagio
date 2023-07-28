package br.com.cwi.crescer.api.validator;

import br.com.cwi.crescer.api.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
public class SelfFriendshipValidateTest {

    private SelfFriendshipValidate selfFriendshipValidate = new SelfFriendshipValidate();

    @Test
    @DisplayName("Deve lançar exceção quando usuário tentar ser amigo de si mesmo")
    public void shouldThrowExceptionWhenUserTriesToBeFriendOfHimself() {
        User me = User.builder().id(1L).username("Fulano").build();

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
            selfFriendshipValidate.validate(me, me);
        });

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        Assertions.assertEquals("O usuário não pode ser amigo de si mesmo", exception.getReason());
    }

    @Test
    @DisplayName("Não deve lançar exceção quando usuários são diferentes")
    public void shouldNotThrowExceptionWhenUsersAreDifferent() {
        User me = User.builder().id(1L).username("Fulano").build();
        User friend = User.builder().id(2L).username("Sicrano").build();

        Assertions.assertDoesNotThrow(() -> {
            selfFriendshipValidate.validate(me, friend);
        });
    }

}
