package br.com.cwi.crescer.api.service.user;

import br.com.cwi.crescer.api.controller.dtos.response.UserResponse;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.mapper.UserMapper;
import br.com.cwi.crescer.api.repository.UserRepository;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ListUsersServiceTest {

    @InjectMocks
    private ListUsersService listUsersService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GetUserAuthenticatedService getUserAuthenticatedService;

    @Mock
    private UserMapper userMapper;

    @Test
    @DisplayName("Deve listar usuários com sucesso")
    void shouldListUsersSuccessfully() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        User authenticatedUser = new User();
        authenticatedUser.setId(1L);
        String search = null;

        Page<User> userPage = new PageImpl<>(Arrays.asList(new User(), new User()));

        given(getUserAuthenticatedService.get()).willReturn(authenticatedUser);
        given(userRepository.findByEmailNot(authenticatedUser.getEmail(), pageable)).willReturn(userPage);
        given(userMapper.toResponse(any(User.class))).willReturn(new UserResponse());

        // when
        Page<UserResponse> result = listUsersService.list(pageable, search);

        // then
        assertThat(result).isNotNull();

        verify(getUserAuthenticatedService).get();
        verify(userRepository).findByEmailNot(authenticatedUser.getEmail(), pageable);
        verify(userMapper, times(userPage.getContent().size())).toResponse(any(User.class));
    }

    @Test
    @DisplayName("Deve listar usuários com sucesso filtrando pelo nome ou email")
    void shouldListUsersSuccessfullyWithSearchFilter() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        User authenticatedUser = new User();
        authenticatedUser.setId(1L);
        String search = "John";

        Page<User> userPage = new PageImpl<>(Arrays.asList(new User(), new User()));

        given(getUserAuthenticatedService.get()).willReturn(authenticatedUser);
        given(userRepository.findByNameOrEmail(pageable, search, authenticatedUser.getId())).willReturn(userPage);
        given(userMapper.toResponse(any(User.class))).willReturn(new UserResponse());

        // when
        Page<UserResponse> result = listUsersService.list(pageable, search);

        // then
        assertThat(result).isNotNull();

        verify(getUserAuthenticatedService).get();
        verify(userRepository).findByNameOrEmail(pageable, search, authenticatedUser.getId());
        verify(userMapper, times(userPage.getContent().size())).toResponse(any(User.class));
    }
}
