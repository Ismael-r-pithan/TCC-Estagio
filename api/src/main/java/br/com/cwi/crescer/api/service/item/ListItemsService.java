package br.com.cwi.crescer.api.service.item;

import br.com.cwi.crescer.api.controller.dtos.response.ItemResponse;
import br.com.cwi.crescer.api.mapper.ItemMapper;
import br.com.cwi.crescer.api.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListItemsService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemMapper itemMapper;

    public Page<ItemResponse> list(Pageable pageable, String search) {
        if (search != null) {
            return itemRepository.findByNameOrDescription(pageable, search).map(itemMapper::toResponse);
        }
        return itemRepository.findAll(pageable).map(itemMapper::toResponse);
    }

}
