package br.com.cwi.crescer.api.service.item;

import br.com.cwi.crescer.api.controller.dtos.request.item.BuyItemRequest;
import br.com.cwi.crescer.api.domain.Item;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.repository.UserRepository;
import br.com.cwi.crescer.api.service.user.GetUserAuthenticatedService;
import br.com.cwi.crescer.api.validator.HasMoneyValidate;
import br.com.cwi.crescer.api.validator.UserAlreadyOwnsItemValidate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ExtendWith(MockitoExtension.class)
public class BuyItemServiceTest {

    @InjectMocks
    private BuyItemService buyItemService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GetItemByIdService getItemByIdService;

    @Mock
    private HasMoneyValidate hasMoneyValidate;

    @Mock
    private UserAlreadyOwnsItemValidate userAlreadyOwnsItemValidate;

    @Mock
    private GetUserAuthenticatedService getUserAuthenticatedService;

    @Test
    @DisplayName("Deve comprar o item com sucesso")
    void deveComprarItemComSucesso() {
        // Given
        User user = User.builder().id(1L).username("John").money(100).items(new ArrayList<Item>()).build();
        Item item = Item.builder().id(1L).name("Sword").price(50).build();
        BuyItemRequest request = new BuyItemRequest(1L);

        given(getUserAuthenticatedService.get()).willReturn(user);
        given(getItemByIdService.get(request.getId())).willReturn(item);

        // When
        buyItemService.buyItem(request);

        // Then
        verify(userRepository, times(1)).save(user);
        assertEquals(50, user.getMoney());
        assertTrue(user.getItems().contains(item));
    }

    @Test
    @DisplayName("Deve lançar exceção ao comprar item com dinheiro insuficiente")
    void deveLancarExcecaoAoComprarItemComDinheiroInsuficiente() {
        // Given
        User user = User.builder().id(1L).username("John").money(10).build();
        Item item = Item.builder().id(1L).name("Sword").price(50).build();
        BuyItemRequest request = new BuyItemRequest(1L);

        given(getUserAuthenticatedService.get()).willReturn(user);
        given(getItemByIdService.get(request.getId())).willReturn(item);
        willThrow(new ResponseStatusException(BAD_REQUEST, "Você não possui dinheiro suficiente para comprar o item"))
                .given(hasMoneyValidate).validate(user, item);

        // When
        assertThrows(ResponseStatusException.class, () -> buyItemService.buyItem(request));

        // Then
        verify(userRepository, never()).save(user);
    }

    @Test
    @DisplayName("Deve lançar exceção ao comprar item que o usuário já possui")
    void deveLancarExcecaoAoComprarItemQueOUsuarioJaPossui() {
        // Given
        User user = User.builder().id(1L).username("John").money(100).build();
        Item item = Item.builder().id(1L).name("Sword").price(50).build();
        user.setItems(Collections.singletonList(item));
        BuyItemRequest request = new BuyItemRequest(1L);

        given(getUserAuthenticatedService.get()).willReturn(user);
        given(getItemByIdService.get(request.getId())).willReturn(item);
        willThrow(new ResponseStatusException(BAD_REQUEST, "Você já possui esse item"))
                .given(userAlreadyOwnsItemValidate).validate(user, item.getId());

        // When
        assertThrows(ResponseStatusException.class, () -> buyItemService.buyItem(request));

        // Then
        verify(userRepository, never()).save(user);
    }
}
