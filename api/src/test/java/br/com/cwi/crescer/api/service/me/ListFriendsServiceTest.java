package br.com.cwi.crescer.api.service.me;

import br.com.cwi.crescer.api.controller.dtos.response.FriendshipResponse;
import br.com.cwi.crescer.api.domain.Friendship;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.mapper.FriendshipMapper;
import br.com.cwi.crescer.api.repository.FriendshipRepository;
import br.com.cwi.crescer.api.service.user.GetUserAuthenticatedService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ListFriendsServiceTest {

    @InjectMocks
    private ListFriendsService tested;

    @Mock
    private FriendshipRepository friendshipRepository;

    @Mock
    private FriendshipMapper friendshipMapper;

    @Mock
    private GetUserAuthenticatedService getUserAuthenticatedService;

    @Test
    @DisplayName("Deve listar todas as amizades do usuário logado sem filtro")
    void shouldListAllFriendsWithoutFilter() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        Pageable pageable = PageRequest.of(0, 10);
        List<Friendship> friendships = Arrays.asList(new Friendship(), new Friendship());
        given(getUserAuthenticatedService.get()).willReturn(user);
        given(friendshipRepository.findByUserId(userId, pageable)).willReturn(new PageImpl<>(friendships));
        given(friendshipMapper.toResponse(any(Friendship.class))).willReturn(new FriendshipResponse());

        // When
        Page<FriendshipResponse> result = tested.list(pageable, null);

        // Then
        verify(getUserAuthenticatedService).get();
        verify(friendshipRepository).findByUserId(userId, pageable);
        verify(friendshipMapper, times(2)).toResponse(any(Friendship.class));
        assertEquals(2, result.getContent().size());
    }

    @Test
    @DisplayName("Deve listar todas as amizades do usuário logado com filtro")
    void shouldListAllFriendsWithFilter() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        Pageable pageable = PageRequest.of(0, 10);
        String search = "search";
        List<Friendship> friendships = Arrays.asList(new Friendship(), new Friendship());
        given(getUserAuthenticatedService.get()).willReturn(user);
        given(friendshipRepository.findByUserIdWithFiltro(userId, pageable, search)).willReturn(new PageImpl<>(friendships));
        given(friendshipMapper.toResponse(any(Friendship.class))).willReturn(new FriendshipResponse());

        // When
        Page<FriendshipResponse> result = tested.list(pageable, search);

        // Then
        verify(getUserAuthenticatedService).get();
        verify(friendshipRepository).findByUserIdWithFiltro(userId, pageable, search);
        verify(friendshipMapper, times(2)).toResponse(any(Friendship.class));
        assertEquals(2, result.getContent().size());
    }
}
