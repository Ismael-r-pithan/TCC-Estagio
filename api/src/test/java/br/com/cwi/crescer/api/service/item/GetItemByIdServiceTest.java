package br.com.cwi.crescer.api.service.item;

import br.com.cwi.crescer.api.domain.Item;
import br.com.cwi.crescer.api.repository.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetItemByIdServiceTest {

    @InjectMocks
    private GetItemByIdService getItemByIdService;

    @Mock
    private ItemRepository itemRepository;

    @Test
    @DisplayName("Deve retornar o item com o ID especificado")
    public void testGetItemById() {
        // setup
        Long id = 1L;
        Item item = Item.builder().id(id).name("Item de Teste").price(10).build();

        when(itemRepository.findById(id)).thenReturn(Optional.of(item));

        // test
        Item result = getItemByIdService.get(id);

        // assert
        assertNotNull(result);
        assertEquals(item, result);
    }

    @Test
    @DisplayName("Deve lançar exceção quando não encontrar item com o ID especificado")
    public void testGetItemByIdNotFound() {
        // setup
        Long id = 1L;

        when(itemRepository.findById(id)).thenReturn(Optional.empty());

        // assert
        assertThrows(ResponseStatusException.class, () -> getItemByIdService.get(id));
    }
}
