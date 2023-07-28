package br.com.cwi.crescer.api.service.me;

import br.com.cwi.crescer.api.controller.dtos.response.ItemResponse;
import br.com.cwi.crescer.api.domain.Item;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.mapper.ItemMapper;
import br.com.cwi.crescer.api.service.user.GetUserAuthenticatedService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class ListUserItemsServiceTest {

    @InjectMocks
    private ListUserItemsService tested;

    @Mock
    private GetUserAuthenticatedService getUserAuthenticatedService;

    @Mock
    private ItemMapper itemMapper;

    @Test
    @DisplayName("Deve retornar a lista de itens do usuário logado")
    void shouldReturnListOfUserItems() {
        User user = new User();

        Item item1 = Item.builder().id(1L).name("Item 1").build();
        Item item2 = Item.builder().id(2L).name("Item 2").build();
        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        user.setItems(items);

        given(getUserAuthenticatedService.get()).willReturn(user);

        ItemResponse itemResponse1 = new ItemResponse();
        itemResponse1.setName("Item 1");
        ItemResponse itemResponse2 = new ItemResponse();
        itemResponse2.setName("Item 2");

        given(itemMapper.toResponse(item1)).willReturn(itemResponse1);
        given(itemMapper.toResponse(item2)).willReturn(itemResponse2);

        // When
        List<ItemResponse> result = tested.list();

        // Then
        assertEquals(2, result.size());
        assertEquals(itemResponse1, result.get(0));
        assertEquals(itemResponse2, result.get(1));

        verify(getUserAuthenticatedService).get();
        verify(itemMapper).toResponse(item1);
        verify(itemMapper).toResponse(item2);
        verifyNoMoreInteractions(getUserAuthenticatedService, itemMapper);
    }

}
