package br.com.cwi.crescer.api.service.item;

import br.com.cwi.crescer.api.domain.Item;
import br.com.cwi.crescer.api.domain.enums.ItemType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
public class ValidateIsBannerService {

    @Autowired
    private GetItemByIdService getItemByIdService;

    public void validate(Long itemId) {
        Item item = getItemByIdService.get(itemId);

        if (!Objects.equals(item.getType(), ItemType.CAPA.toString())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item não é um banner");
        }
    }
}
