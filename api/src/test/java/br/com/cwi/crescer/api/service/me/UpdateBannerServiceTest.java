package br.com.cwi.crescer.api.service.me;

import br.com.cwi.crescer.api.controller.dtos.request.logado.UpdateBannerRequest;
import br.com.cwi.crescer.api.domain.Item;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.repository.UserRepository;
import br.com.cwi.crescer.api.service.item.GetItemByIdService;
import br.com.cwi.crescer.api.service.item.ValidateIsBannerService;
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
public class UpdateBannerServiceTest {
    @InjectMocks
    private UpdateBannerService updateBannerService;

    @Mock
    private GetUserAuthenticatedService getUserAuthenticatedService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GetItemByIdService getItemByIdService;

    @Mock
    private UserHasNoItemValidate userHasNoItemValidate;

    @Mock
    private ValidateIsBannerService validateIsBannerService;

    @Test
    @DisplayName("Deve atualizar o banner do usuário")
    void deveAtualizarBanner() {
        User user = new User();
        Item banner = new Item();
        banner.setImageUrl("https://example.com/banner.png");

        when(getUserAuthenticatedService.get()).thenReturn(user);
        when(getItemByIdService.get(1L)).thenReturn(banner);
        doNothing().when(userHasNoItemValidate).validate(user, 1L);
        doNothing().when(validateIsBannerService).validate(1L);

        // when
        UpdateBannerRequest request = new UpdateBannerRequest();
        request.setBannerId(1L);
        updateBannerService.update(request);

        // then
        verify(getUserAuthenticatedService, times(1)).get();
        verify(getItemByIdService, times(1)).get(1L);
        verify(userHasNoItemValidate, times(1)).validate(user, 1L);
        verify(validateIsBannerService, times(1)).validate(1L);
        verify(userRepository, times(1)).save(user);
        assertEquals(banner.getImageUrl(), user.getBannerUrl());
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não tem o item")
    void deveLancarExcecaoQuandoUsuarioNaoTemItem() {
        User user = new User();
        UpdateBannerRequest request = new UpdateBannerRequest();
        request.setBannerId(1L);

        when(getUserAuthenticatedService.get()).thenReturn(user);
        doThrow(ResponseStatusException.class).when(userHasNoItemValidate).validate(user, 1L);

        // when/then
        assertThrows(ResponseStatusException.class, () -> updateBannerService.update(request));

        verify(getUserAuthenticatedService, times(1)).get();
        verify(userHasNoItemValidate, times(1)).validate(user, 1L);
        verifyNoMoreInteractions(getItemByIdService, validateIsBannerService, userRepository);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o item não é um banner")
    void deveLancarExcecaoQuandoItemNaoEhBanner() {
        User user = new User();
        UpdateBannerRequest request = new UpdateBannerRequest();
        request.setBannerId(1L);

        when(getUserAuthenticatedService.get()).thenReturn(user);
        doNothing().when(userHasNoItemValidate).validate(user, 1L);
        doThrow(ResponseStatusException.class).when(validateIsBannerService).validate(1L);

        // when/then
        assertThrows(ResponseStatusException.class, () -> updateBannerService.update(request));

        verify(getUserAuthenticatedService, times(1)).get();
        verify(userHasNoItemValidate, times(1)).validate(user, 1L);
        verify(validateIsBannerService, times(1)).validate(1L);
        verifyNoMoreInteractions(userRepository);
    }
}
