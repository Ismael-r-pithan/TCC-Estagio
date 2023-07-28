package br.com.cwi.crescer.api.service.item;

import br.com.cwi.crescer.api.domain.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoadDefaultItemsServiceTest {

    @InjectMocks
    private LoadDefaultItemsService loadDefaultItemsService;

    @Mock
    private GetItemByIdService getItemByIdService;

    @Test
    @DisplayName("Deve carregar os itens padrões")
    public void testLoad() {
        // setup
        Item defaultAvatar = Item.builder()
                .id(1000000000L)
                .build();
        Item defaultBanner = Item.builder()
                .id(1000000008L)
                .build();

        when(getItemByIdService.get(1000000000L)).thenReturn(defaultAvatar);
        when(getItemByIdService.get(1000000008L)).thenReturn(defaultBanner);

        // test
        List<Item> result = loadDefaultItemsService.load();

        // assert
        assertEquals(2, result.size());
        assertTrue(result.contains(defaultAvatar));
        assertTrue(result.contains(defaultBanner));
    }
}
