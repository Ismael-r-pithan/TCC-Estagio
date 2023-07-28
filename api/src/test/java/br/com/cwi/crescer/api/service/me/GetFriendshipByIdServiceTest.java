package br.com.cwi.crescer.api.service.me;

import br.com.cwi.crescer.api.domain.Friendship;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class GetFriendshipByIdServiceTest {

    @InjectMocks
    private GetFriendshipByIdService tested;

    @Mock
    private FriendshipRepository friendshipRepository;

    @Test
    @DisplayName("Deve retornar a amizade quando ela existe")
    void shouldReturnFriendshipWhenItExists() {
        Long friendshipId = 1L;
        Friendship friendship = new Friendship();
        friendship.setId(friendshipId);
        given(friendshipRepository.findById(friendshipId)).willReturn(Optional.of(friendship));

        // When
        Friendship result = tested.get(friendshipId);

        // Then
        assertEquals(friendship, result);
        verifyNoMoreInteractions(friendshipRepository);
    }

    @Test
    @DisplayName("Deve lançar exceção quando a amizade não existe")
    void shouldThrowExceptionWhenFriendshipDoesNotExist() {
        Long friendshipId = 1L;
        given(friendshipRepository.findById(friendshipId)).willReturn(Optional.empty());

        // When/Then
        assertThrows(ResponseStatusException.class, () -> tested.get(friendshipId));
        verify(friendshipRepository).findById(friendshipId);
        verifyNoMoreInteractions(friendshipRepository);
    }

}
