package br.com.cwi.crescer.api.service.item;


import br.com.cwi.crescer.api.controller.dtos.request.item.CreateItemRequest;
import br.com.cwi.crescer.api.domain.Item;
import br.com.cwi.crescer.api.mapper.ItemMapper;
import br.com.cwi.crescer.api.repository.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateItemServiceTest {

    @InjectMocks
    private CreateItemService createItemService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemMapper itemMapper;

    @Test
    @DisplayName("Deve criar item com sucesso")
    void testCreateItem() throws IOException {
        // setup
        MultipartFile image = new MockMultipartFile("image.jpg", new byte[0]);

        CreateItemRequest createItemRequest = CreateItemRequest.builder()
                .name("item")
                .price(50)
                .image(image)
                .build();

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");

        Item item = Item.builder()
                .id(1L)
                .name("item")
                .price(50)
                .build();

        when(itemMapper.toEntity(createItemRequest)).thenReturn(item);

        // test
        createItemService.create(createItemRequest, request);

        // assert
        verify(itemRepository).save(item);
        assertNotNull(item.getImageUrl());
    }
}
