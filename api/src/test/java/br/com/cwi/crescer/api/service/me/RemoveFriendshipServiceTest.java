package br.com.cwi.crescer.api.service.me;

import br.com.cwi.crescer.api.domain.Friendship;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.repository.FriendshipRepository;
import br.com.cwi.crescer.api.service.user.GetUserAuthenticatedService;
import br.com.cwi.crescer.api.service.user.GetUserByIdService;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class RemoveFriendshipServiceTest {

    @InjectMocks
    private RemoveFriendshipService tested;

    @Mock
    private GetFriendshipByIdService getFriendshipByIdService;

    @Mock
    private FriendshipRepository friendshipRepository;

    @Mock
    private GetUserAuthenticatedService getUserAuthenticatedService;

    @Mock
    private GetUserByIdService getUserByIdService;

    private static final Long TEST_ID = 1L;

    @Test
    @DisplayName("Deve remover amizade")
    void shouldRemoveFriendship() {
        Friendship friendship = new Friendship();
        User me = new User();
        me.setId(2L);
        User friend = new User();
        friend.setId(3L);
        friendship.setUsuario(me);
        friendship.setFriend(friend);
        given(getFriendshipByIdService.get(TEST_ID)).willReturn(friendship);
        given(getUserAuthenticatedService.get()).willReturn(me);
        given(getUserByIdService.get(friend.getId())).willReturn(friend);

        // When
        tested.remove(TEST_ID);

        // Then
        verify(getFriendshipByIdService).get(TEST_ID);
        verify(getUserAuthenticatedService).get();
        verify(getUserByIdService).get(friend.getId());
        verify(friendshipRepository).deleteFriendship(me.getId(), friend.getId());
        verifyNoMoreInteractions(getFriendshipByIdService, getUserAuthenticatedService, getUserByIdService, friendshipRepository);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar remover amizade de outro usuário")
    void shouldThrowExceptionWhenRemovingFriendshipOfAnotherUser() {
        Friendship friendship = new Friendship();
        User me = new User();
        me.setId(2L);
        User friend = new User();
        friend.setId(3L);
        friendship.setUsuario(friend);
        friendship.setFriend(me);
        given(getFriendshipByIdService.get(TEST_ID)).willReturn(friendship);
        given(getUserAuthenticatedService.get()).willReturn(me);

        // When
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> tested.remove(TEST_ID));

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        verify(getFriendshipByIdService).get(TEST_ID);
        verify(getUserAuthenticatedService).get();
        verifyNoMoreInteractions(getFriendshipByIdService, getUserAuthenticatedService, friendshipRepository);
    }
}

