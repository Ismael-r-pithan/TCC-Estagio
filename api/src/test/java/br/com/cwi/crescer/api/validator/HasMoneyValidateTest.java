package br.com.cwi.crescer.api.validator;

import br.com.cwi.crescer.api.domain.Item;
import br.com.cwi.crescer.api.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class HasMoneyValidateTest {

    @InjectMocks
    private HasMoneyValidate hasMoneyValidate;

    @Test
    @DisplayName("Deve validar que o usuário possui dinheiro suficiente para comprar o item")
    void deveValidarQueUsuarioPossuiDinheiroSuficiente() {
        User user = User.builder()
                .id(1L)
                .username("João")
                .email("joao@test.com")
                .money(100)
                .build();

        Item item = Item.builder()
                .id(1L)
                .name("Item1")
                .description("Descrição do Item1")
                .price(50)
                .build();

        Assertions.assertDoesNotThrow(() -> hasMoneyValidate.validate(user, item));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o usuário não possui dinheiro suficiente para comprar o item")
    void deveLancarExcecaoQuandoUsuarioNaoPossuiDinheiroSuficiente() {
        User user = User.builder()
                .id(1L)
                .username("João")
                .email("joao@test.com")
                .money(30)
                .build();

        Item item = Item.builder()
                .id(1L)
                .name("Item1")
                .description("Descrição do Item1")
                .price(50)
                .build();

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> hasMoneyValidate.validate(user, item));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Você não possui dinheiro sufuciente para comprar o item", exception.getReason());
    }

}

