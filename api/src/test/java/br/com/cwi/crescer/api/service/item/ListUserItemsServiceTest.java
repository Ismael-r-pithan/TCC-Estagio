package br.com.cwi.crescer.api.service.item;

import br.com.cwi.crescer.api.controller.dtos.response.ItemResponse;
import br.com.cwi.crescer.api.domain.Item;
import br.com.cwi.crescer.api.mapper.ItemMapper;
import br.com.cwi.crescer.api.repository.ItemRepository;
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

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListUserItemsServiceTest {

    @InjectMocks
    private ListItemsService listItemsService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemMapper itemMapper;

    @Test
    @DisplayName("Deve retornar lista de items com sucesso")
    public void testListItems() {
        // setup
        Pageable pageable = PageRequest.of(0, 10);
        String search = null;
        List<Item> items = Arrays.asList(Item.builder().id(1L).name("Item 1").description("Description 1").price(10).build(),
                Item.builder().id(1L).name("Item 2").description("Description 2").price(20).build());
        Page<Item> itemPage = new PageImpl<>(items);

        when(itemRepository.findAll(pageable)).thenReturn(itemPage);
        when(itemMapper.toResponse(any())).thenReturn(new ItemResponse());

        // test
        Page<ItemResponse> result = listItemsService.list(pageable, search);

        // assert
        assertNotNull(result);
        assertEquals(itemPage.getTotalElements(), result.getTotalElements());
        assertEquals(itemPage.getTotalPages(), result.getTotalPages());
        verify(itemRepository).findAll(pageable);
        verify(itemMapper, times(items.size())).toResponse(any());
    }

    @Test
    @DisplayName("Deve retornar lista de items filtrada por nome ou descrição com sucesso")
    public void testListItemsFiltered() {
        // setup
        Pageable pageable = PageRequest.of(0, 10);
        String search = "Item";
        List<Item> items = Arrays.asList(Item.builder().id(1L).name("Item 1").description("Description 1").price(10).build(),
                Item.builder().id(1L).name("Item 2").description("Description 2").price(20).build());
        Page<Item> itemPage = new PageImpl<>(items);

        when(itemRepository.findByNameOrDescription(pageable, search)).thenReturn(itemPage);
        when(itemMapper.toResponse(any())).thenReturn(new ItemResponse());

        // test
        Page<ItemResponse> result = listItemsService.list(pageable, search);

        // assert
        assertNotNull(result);
        assertEquals(itemPage.getTotalElements(), result.getTotalElements());
        assertEquals(itemPage.getTotalPages(), result.getTotalPages());
        verify(itemRepository).findByNameOrDescription(pageable, search);
        verify(itemMapper, times(items.size())).toResponse(any());
    }
}
