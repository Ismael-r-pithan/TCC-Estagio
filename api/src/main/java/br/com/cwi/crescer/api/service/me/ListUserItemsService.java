package br.com.cwi.crescer.api.service.me;

import br.com.cwi.crescer.api.controller.dtos.response.ItemResponse;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.mapper.ItemMapper;
import br.com.cwi.crescer.api.service.user.GetUserAuthenticatedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListUserItemsService {

    @Autowired
    private GetUserAuthenticatedService getUserAuthenticatedService;

    @Autowired
    private ItemMapper itemMapper;

    public List<ItemResponse> list() {
        User logado = getUserAuthenticatedService.get();

        return logado.getItems().stream().map(item -> itemMapper.toResponse(item)).collect(Collectors.toList());
    }
}
