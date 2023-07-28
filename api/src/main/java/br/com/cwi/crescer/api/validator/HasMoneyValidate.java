package br.com.cwi.crescer.api.validator;

import br.com.cwi.crescer.api.domain.Item;
import br.com.cwi.crescer.api.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class HasMoneyValidate {

    public void validate(User user, Item item) {
        if (user.getMoney() < item.getPrice()) {
            throw new ResponseStatusException(BAD_REQUEST, "Você não possui dinheiro sufuciente para comprar o item");
        }
    }
}
