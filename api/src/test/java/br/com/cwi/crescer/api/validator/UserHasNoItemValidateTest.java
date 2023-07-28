package br.com.cwi.crescer.api.validator;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Collections;

import br.com.cwi.crescer.api.domain.Item;
import br.com.cwi.crescer.api.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
public class UserHasNoItemValidateTest {

    @InjectMocks
    private UserHasNoItemValidate tested;

    @Mock
    private User user;

    @Test
    @DisplayName("Deve lançar exceção quando usuário não possui item")
    void shouldThrowExceptionWhenUserDoesNotHaveItem() {
        // Mockando o comportamento do usuário sem o item
        when(user.getItems()).thenReturn(Collections.emptyList());

        // Validando se a exceção é lançada quando o item não existe
        assertThrows(ResponseStatusException.class, () -> tested.validate(user, 1L));
        verify(user).getItems();
    }

    @Test
    @DisplayName("Não deve lançar exceção quando usuário possui item")
    void shouldNotThrowExceptionWhenUserHasItem() {
        // Mockando o comportamento do usuário com o item
        Item item = new Item();
        item.setId(1L);
        when(user.getItems()).thenReturn(Collections.singletonList(item));

        // Validando se a exceção não é lançada quando o item existe
        tested.validate(user, 1L);
        verify(user).getItems();
    }
}
