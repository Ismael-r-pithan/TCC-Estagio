package br.com.cwi.crescer.api.service.me;

import br.com.cwi.crescer.api.controller.dtos.request.logado.UpdateAvatarRequest;
import br.com.cwi.crescer.api.domain.Item;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.repository.UserRepository;
import br.com.cwi.crescer.api.service.item.GetItemByIdService;
import br.com.cwi.crescer.api.service.item.ValidateIsAvatarService;
import br.com.cwi.crescer.api.service.user.GetUserAuthenticatedService;
import br.com.cwi.crescer.api.validator.UserHasNoItemValidate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateAvatarServiceTest {

    @InjectMocks
    private UpdateAvatarService updateAvatarService;

    @Mock
    private GetUserAuthenticatedService getUserAuthenticatedService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GetItemByIdService getItemByIdService;

    @Mock
    private UserHasNoItemValidate userHasNoItemValidate;

    @Mock
    private ValidateIsAvatarService validateIsAvatarService;

    @Test
    @DisplayName("Deve atualizar o avatar do usuário")
    void deveAtualizarAvatar() {
        User user = new User();
        Item avatar = new Item();
        avatar.setImageUrl("https://example.com/avatar.png");

        when(getUserAuthenticatedService.get()).thenReturn(user);
        when(getItemByIdService.get(1L)).thenReturn(avatar);
        doNothing().when(userHasNoItemValidate).validate(user, 1L);
        doNothing().when(validateIsAvatarService).validate(1L);

        // when
        UpdateAvatarRequest request = new UpdateAvatarRequest();
        request.setAvatarId(1L);
        updateAvatarService.update(request);

        // then
        verify(getUserAuthenticatedService, times(1)).get();
        verify(getItemByIdService, times(1)).get(1L);
        verify(userHasNoItemValidate, times(1)).validate(user, 1L);
        verify(validateIsAvatarService, times(1)).validate(1L);
        verify(userRepository, times(1)).save(user);
        assertEquals(avatar.getImageUrl(), user.getImageProfile());
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não tem o item")
    void deveLancarExcecaoQuandoUsuarioNaoTemItem() {
        User user = new User();
        UpdateAvatarRequest request = new UpdateAvatarRequest();
        request.setAvatarId(1L);

        when(getUserAuthenticatedService.get()).thenReturn(user);
        doThrow(ResponseStatusException.class).when(userHasNoItemValidate).validate(user, 1L);

        // when/then
        assertThrows(ResponseStatusException.class, () -> updateAvatarService.update(request));

        verify(getUserAuthenticatedService, times(1)).get();
        verify(userHasNoItemValidate, times(1)).validate(user, 1L);
        verifyNoMoreInteractions(getItemByIdService, validateIsAvatarService, userRepository);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o item não é um avatar")
    void deveLancarExcecaoQuandoItemNaoEhAvatar() {
        User user = new User();
        Item item = new Item();
        UpdateAvatarRequest request = new UpdateAvatarRequest();
        request.setAvatarId(1L);

        when(getUserAuthenticatedService.get()).thenReturn(user);
        doNothing().when(userHasNoItemValidate).validate(user, 1L);
        doThrow(ResponseStatusException.class).when(validateIsAvatarService).validate(1L);

        // when/then
        assertThrows(ResponseStatusException.class, () -> updateAvatarService.update(request));

        verify(getUserAuthenticatedService, times(1)).get();
        verify(userHasNoItemValidate, times(1)).validate(user, 1L);
        verify(validateIsAvatarService, times(1)).validate(1L);
        verifyNoMoreInteractions(userRepository);
    }

}

