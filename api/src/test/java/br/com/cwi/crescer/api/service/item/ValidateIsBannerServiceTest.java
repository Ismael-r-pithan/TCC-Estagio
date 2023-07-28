package br.com.cwi.crescer.api.service.item;

import br.com.cwi.crescer.api.domain.Item;
import br.com.cwi.crescer.api.domain.enums.ItemType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ValidateIsBannerServiceTest {

    @InjectMocks
    private ValidateIsBannerService validateIsBannerService;

    @Mock
    private GetItemByIdService getItemByIdService;

    @Test
    @DisplayName("Deve lançar exceção quando o item não for um banner")
    void shouldThrowExceptionWhenItemIsNotBanner() {
        // given
        Long itemId = 1L;
        Item item = new Item();
        item.setType(ItemType.AVATAR.toString());

        given(getItemByIdService.get(itemId)).willReturn(item);

        // when
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            validateIsBannerService.validate(itemId);
        });

        // then
        assertThat(exception.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exception.getReason()).isEqualTo("Item não é um banner");
    }

    @Test
    @DisplayName("Não deve lançar exceção quando o item for um banner")
    void shouldNotThrowExceptionWhenItemIsBanner() {
        // given
        Long itemId = 1L;
        Item item = new Item();
        item.setType(ItemType.CAPA.toString());

        given(getItemByIdService.get(itemId)).willReturn(item);

        // when
        Throwable exception = catchThrowable(() -> validateIsBannerService.validate(itemId));

        // then
        assertThat(exception).isNull();
    }
}


