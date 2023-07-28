package br.com.cwi.crescer.api.service.item;


import br.com.cwi.crescer.api.domain.Item;
import br.com.cwi.crescer.api.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class GetItemByIdService {
    @Autowired
    private ItemRepository itemRepository;
    public Item get(Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Item não encontrado"));
    }
}
