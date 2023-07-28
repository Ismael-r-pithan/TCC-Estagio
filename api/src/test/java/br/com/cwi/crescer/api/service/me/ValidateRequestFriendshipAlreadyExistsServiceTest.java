package br.com.cwi.crescer.api.service.me;

import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.repository.FriendshipRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ValidateRequestFriendshipAlreadyExistsServiceTest {

    @Mock
    private FriendshipRepository friendshipRepository;

    @InjectMocks
    private ValidateRequestFriendshipAlreadyExistsService service;

    @Test
    @DisplayName("Deve lançar exceção quando amizade já foi solicitada")
    void shouldThrowExceptionWhenRequestFriendshipAlreadyExists() {
        User me = new User();
        me.setId(1L);
        User amigo = new User();
        amigo.setId(2L);
        when(friendshipRepository.existsRequestFriendship(me, amigo)).thenReturn(true);

        // when
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> service.validate(me, amigo));

        // then
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Amizade já foi solicitada", exception.getReason());
    }

    @Test
    @DisplayName("Não deve lançar exceção quando amizade não foi solicitada")
    void shouldNotThrowExceptionWhenRequestFriendshipDoesNotExists() {
        User me = new User();
        me.setId(1L);
        User amigo = new User();
        amigo.setId(2L);
        when(friendshipRepository.existsRequestFriendship(me, amigo)).thenReturn(false);

        // when
        service.validate(me, amigo);

        // then
        verify(friendshipRepository, times(1)).existsRequestFriendship(me, amigo);
    }
}

