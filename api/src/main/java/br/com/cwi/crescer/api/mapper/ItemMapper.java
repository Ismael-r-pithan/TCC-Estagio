package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.dtos.request.item.CreateItemRequest;
import br.com.cwi.crescer.api.controller.dtos.response.ItemResponse;
import br.com.cwi.crescer.api.domain.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface ItemMapper {
    ItemResponse toResponse(Item entity);

    @Mapping(target = "imageUrl", ignore = true)
    Item toEntity(CreateItemRequest request);
}
