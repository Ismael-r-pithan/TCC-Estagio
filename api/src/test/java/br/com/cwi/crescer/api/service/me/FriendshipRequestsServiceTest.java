package br.com.cwi.crescer.api.service.me;

import br.com.cwi.crescer.api.controller.dtos.response.FriendshipResponse;
import br.com.cwi.crescer.api.domain.Friendship;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.domain.enums.Status;
import br.com.cwi.crescer.api.mapper.FriendshipMapper;
import br.com.cwi.crescer.api.repository.FriendshipRepository;
import br.com.cwi.crescer.api.service.user.GetUserAuthenticatedService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class FriendshipRequestsServiceTest {

    @InjectMocks
    private FriendshipRequestsService tested;

    @Mock
    private FriendshipRepository friendshipRepository;

    @Mock
    private GetUserAuthenticatedService getUserAuthenticatedService;

    @Mock
    private FriendshipMapper friendshipMapper;

    @Test
    @DisplayName("Deve listar as amizades pendentes do usuário autenticado")
    void shouldListPendingFriendshipsOfAuthenticatedUser() {
        User authenticatedUser = new User();
        authenticatedUser.setId(1L);
        Friendship friendship1 = new Friendship();
        friendship1.setId(1L);
        friendship1.setUsuario(authenticatedUser);
        friendship1.setFriend(new User());
        friendship1.setStatus(Status.PENDENTE);
        Friendship friendship2 = new Friendship();
        friendship2.setId(2L);
        friendship2.setUsuario(authenticatedUser);
        friendship2.setFriend(new User());
        friendship2.setStatus(Status.PENDENTE);
        List<Friendship> friendships = Arrays.asList(friendship1, friendship2);
        given(getUserAuthenticatedService.get()).willReturn(authenticatedUser);
        given(friendshipRepository.findByUserIdWithAmizadePendente(authenticatedUser.getId())).willReturn(friendships);
        given(friendshipMapper.toResponse(friendship1)).willReturn(new FriendshipResponse());
        given(friendshipMapper.toResponse(friendship2)).willReturn(new FriendshipResponse());

        // When
        List<FriendshipResponse> result = tested.list();

        // Then
        verify(getUserAuthenticatedService).get();
        verify(friendshipRepository).findByUserIdWithAmizadePendente(authenticatedUser.getId());
        verify(friendshipMapper).toResponse(friendship1);
        verify(friendshipMapper).toResponse(friendship2);
        assertEquals(2, result.size());
        verifyNoMoreInteractions(getUserAuthenticatedService, friendshipRepository, friendshipMapper);
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando o usuário autenticado não tiver amizades pendentes")
    void shouldReturnEmptyListWhenAuthenticatedUserHasNoPendingFriendships() {
        User authenticatedUser = new User();
        authenticatedUser.setId(1L);
        given(getUserAuthenticatedService.get()).willReturn(authenticatedUser);
        given(friendshipRepository.findByUserIdWithAmizadePendente(authenticatedUser.getId())).willReturn(Collections.emptyList());

        // When
        List<FriendshipResponse> result = tested.list();

        // Then
        verify(getUserAuthenticatedService).get();
        verify(friendshipRepository).findByUserIdWithAmizadePendente(authenticatedUser.getId());
        assertTrue(result.isEmpty());
        verifyNoMoreInteractions(getUserAuthenticatedService, friendshipRepository, friendshipMapper);
    }
}
