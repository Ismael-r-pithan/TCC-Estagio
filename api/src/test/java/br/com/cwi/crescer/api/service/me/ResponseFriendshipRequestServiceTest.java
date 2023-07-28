package br.com.cwi.crescer.api.service.me;

import br.com.cwi.crescer.api.controller.dtos.request.logado.ResponseFriendshipRequest;
import br.com.cwi.crescer.api.domain.Friendship;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.domain.enums.Status;
import br.com.cwi.crescer.api.mapper.FriendshipMapper;
import br.com.cwi.crescer.api.repository.FriendshipRepository;
import br.com.cwi.crescer.api.service.user.GetUserAuthenticatedService;
import br.com.cwi.crescer.api.validator.UserWhoRepliedValidate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ResponseFriendshipRequestServiceTest {

    @InjectMocks
    private ResponseFriendshipRequestService responseFriendshipRequestService;

    @Mock
    private FriendshipRepository friendshipRepository;

    @Mock
    private GetFriendshipByIdService getFriendshipByIdService;

    @Mock
    private FriendshipMapper friendshipMapper;

    @Mock
    private UserWhoRepliedValidate userWhoRepliedValidate;

    @Mock
    private GetUserAuthenticatedService getUserAuthenticatedService;

    @Test
    @DisplayName("Deve aceitar solicitação de amizade")
    void deveAceitarSolicitacaoDeAmizade() {
        Long friendshipId = 1L;
        Friendship requestFriendship = new Friendship();
        User me = new User();
        User friend = new User();

        requestFriendship.setId(friendshipId);
        requestFriendship.setUsuario(me);
        requestFriendship.setFriend(friend);

        when(getFriendshipByIdService.get(friendshipId)).thenReturn(requestFriendship);
        when(getUserAuthenticatedService.get()).thenReturn(me);

        Friendship newFriendship = new Friendship();
        newFriendship.setUsuario(requestFriendship.getFriend());
        newFriendship.setFriend(requestFriendship.getUsuario());
        newFriendship.setStatus(Status.ACEITA);
        newFriendship.setCreatedAt(LocalDateTime.now());

        when(friendshipMapper.toEntity(requestFriendship.getFriend(), requestFriendship.getUsuario())).thenReturn(newFriendship);

        // when
        ResponseFriendshipRequest request = new ResponseFriendshipRequest();
        request.setIdRequest(friendshipId);
        request.setResponse(Status.ACEITA);
        responseFriendshipRequestService.response(request);

        // then
        verify(getFriendshipByIdService, times(1)).get(friendshipId);
        verify(getUserAuthenticatedService, times(1)).get();
        verify(userWhoRepliedValidate, times(1)).validate(me, friend);
        verify(friendshipRepository, times(1)).save(requestFriendship);
        verify(friendshipMapper, times(1)).toEntity(requestFriendship.getFriend(), requestFriendship.getUsuario());
        verify(friendshipRepository, times(1)).save(newFriendship);
    }

    @Test
    @DisplayName("Deve rejeitar solicitação de amizade")
    void deveRejeitarSolicitacaoDeAmizade() {
        // given
        Long friendshipId = 1L;
        Friendship requestFriendship = new Friendship();
        User me = new User();
        User friend = new User();

        requestFriendship.setId(friendshipId);
        requestFriendship.setUsuario(me);
        requestFriendship.setFriend(friend);

        when(getFriendshipByIdService.get(friendshipId)).thenReturn(requestFriendship);
        when(getUserAuthenticatedService.get()).thenReturn(me);

        // when
        ResponseFriendshipRequest request = new ResponseFriendshipRequest();
        request.setIdRequest(friendshipId);
        request.setResponse(Status.REJEITADA);
        responseFriendshipRequestService.response(request);

        // then
        verify(getFriendshipByIdService, times(1)).get(friendshipId);
        verify(getUserAuthenticatedService, times(1)).get();
        verify(userWhoRepliedValidate, times(1)).validate(me, friend);
        verify(friendshipRepository, times(1)).delete(requestFriendship);
    }

}
