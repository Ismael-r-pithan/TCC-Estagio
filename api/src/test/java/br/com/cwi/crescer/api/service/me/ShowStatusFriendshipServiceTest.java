package br.com.cwi.crescer.api.service.me;

import br.com.cwi.crescer.api.domain.Friendship;
import br.com.cwi.crescer.api.domain.enums.Status;
import br.com.cwi.crescer.api.repository.FriendshipRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShowStatusFriendshipServiceTest {

    @InjectMocks
    private ShowStatusFriendshipService showStatusFriendshipService;

    @Mock
    private FriendshipRepository friendshipRepository;

    @Test
    @DisplayName("Deve retornar o status da amizade")
    void deveRetornarStatusAmizade() {
        Long friendshipId = 1L;
        Friendship friendship = new Friendship();
        friendship.setId(friendshipId);
        friendship.setStatus(Status.ACEITA);
        when(friendshipRepository.findById(friendshipId)).thenReturn(Optional.of(friendship));

        // when
        Status status = showStatusFriendshipService.showStatus(friendshipId);

        // then
        assertEquals(Status.ACEITA, status);
        verify(friendshipRepository, times(1)).findById(friendshipId);
    }

    @Test
    @DisplayName("Deve retornar erro caso amizade não seja encontrada")
    void deveRetornarErroAmizadeNaoEncontrada() {
        Long friendshipId = 1L;
        when(friendshipRepository.findById(friendshipId)).thenReturn(Optional.empty());

        // when/then
        assertThrows(ResponseStatusException.class, () -> showStatusFriendshipService.showStatus(friendshipId));
        verify(friendshipRepository, times(1)).findById(friendshipId);
    }

}

