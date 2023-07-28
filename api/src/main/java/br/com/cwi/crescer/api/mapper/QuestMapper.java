package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.dtos.response.QuestResponse;
import br.com.cwi.crescer.api.domain.Quest;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface QuestMapper {

    QuestResponse toResponse(Quest entity);
}
