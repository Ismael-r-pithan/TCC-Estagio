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
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ValidateIsAvatarServiceTest {

    @InjectMocks
    private ValidateIsAvatarService validateIsAvatarService;

    @Mock
    private GetItemByIdService getItemByIdService;

    @Test
    @DisplayName("Deve lançar exceção quando o item não for um avatar")
    void shouldThrowExceptionWhenItemIsNotAvatar() {
        // given
        Long itemId = 1L;
        Item item = new Item();
        item.setType(ItemType.CAPA.toString());

        given(getItemByIdService.get(itemId)).willReturn(item);

        // when
        Throwable exception = null;
        try {
            validateIsAvatarService.validate(itemId);
        } catch (Throwable e) {
            exception = e;
        }

        // then
        assertThat(exception).isInstanceOf(ResponseStatusException.class);
        ResponseStatusException responseStatusException = (ResponseStatusException) exception;
        assertThat(responseStatusException.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }


    @Test
    @DisplayName("Não deve lançar exceção quando o item for um avatar")
    void shouldNotThrowExceptionWhenItemIsAvatar() {
        // given
        Long itemId = 1L;
        Item item = new Item();
        item.setType(ItemType.AVATAR.toString());

        given(getItemByIdService.get(itemId)).willReturn(item);

        // when
        Throwable exception = catchThrowable(() -> validateIsAvatarService.validate(itemId));

        // then
        assertThat(exception).isNull();
    }
}
