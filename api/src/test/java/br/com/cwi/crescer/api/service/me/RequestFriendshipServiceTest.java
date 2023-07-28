package br.com.cwi.crescer.api.service.me;

import br.com.cwi.crescer.api.domain.Friendship;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.mapper.FriendshipMapper;
import br.com.cwi.crescer.api.repository.FriendshipRepository;
import br.com.cwi.crescer.api.repository.UserRepository;
import br.com.cwi.crescer.api.service.user.GetUserAuthenticatedService;
import br.com.cwi.crescer.api.service.user.GetUserByIdService;
import br.com.cwi.crescer.api.validator.SelfFriendshipValidate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class RequestFriendshipServiceTest {

    @InjectMocks
    private RequestFriendshipService tested;

    @Mock
    private FriendshipRepository friendshipRepository;

    @Mock
    private GetUserByIdService getUserByIdService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FriendshipMapper friendshipMapper;

    @Mock
    private ValidateFriendshipAlreadyExistsService validateFriendshipAlreadyExistsService;

    @Mock
    private SelfFriendshipValidate selfFriendshipValidate;

    @Mock
    private GetUserAuthenticatedService getUserAuthenticatedService;

    @Mock
    private ValidateRequestFriendshipAlreadyExistsService validateRequestFriendshipAlreadyExistsService;

    @Test
    @DisplayName("Deve solicitar amizade")
    void deveSolicitarAmizade() {
        Long friendId = 2L;
        User user = new User();
        user.setId(1L);
        when(getUserAuthenticatedService.get()).thenReturn(user);

        User friend = new User();
        friend.setId(friendId);
        when(getUserByIdService.get(friendId)).thenReturn(friend);

        Friendship friendship = new Friendship();
        when(friendshipMapper.toEntity(user, friend)).thenReturn(friendship);

        when(friendshipRepository.save(friendship)).thenReturn(friendship);

        // when
        tested.solicitar(friendId);

        // then
        verify(getUserAuthenticatedService, times(1)).get();
        verify(getUserByIdService, times(1)).get(friendId);
        verify(validateFriendshipAlreadyExistsService, times(1)).validate(user, friend);
        verify(selfFriendshipValidate, times(1)).validate(user, friend);
        verify(validateRequestFriendshipAlreadyExistsService, times(1)).validate(user, friend);
        verify(friendshipMapper, times(1)).toEntity(user, friend);
        verify(friendshipRepository, times(1)).save(friendship);
    }
}
