package br.com.cwi.crescer.api.service.me;

import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.repository.FriendshipRepository;
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
public class ValidateFriendshipAlreadyExistsServiceTest {

    @InjectMocks
    private ValidateFriendshipAlreadyExistsService tested;

    @Mock
    private FriendshipRepository friendshipRepository;

    @Test
    @DisplayName("Deve lançar exceção se a amizade já existir")
    void shouldThrowExceptionIfFriendshipAlreadyExists() {
        User me = User.builder().id(1L).username("user1").build();
        User amigo = User.builder().id(2L).username("user2").build();
        Mockito.when(friendshipRepository.existsFriendship(me, amigo)).thenReturn(true);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> tested.validate(me, amigo));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Os usuários já são amigos", exception.getReason());
    }

    @Test
    @DisplayName("Não deve lançar exceção se a amizade não existir")
    void shouldNotThrowExceptionIfFriendshipDoesNotExist() {
        User me = User.builder().id(1L).username("user1").build();
        User amigo = User.builder().id(2L).username("user2").build();
        Mockito.when(friendshipRepository.existsFriendship(me, amigo)).thenReturn(false);

        assertDoesNotThrow(() -> tested.validate(me, amigo));
    }
}
