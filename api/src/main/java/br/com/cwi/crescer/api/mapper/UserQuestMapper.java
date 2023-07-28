package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.dtos.response.UserQuestResponse;
import br.com.cwi.crescer.api.domain.Quest;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.domain.UserQuest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserQuestMapper {

    @Mapping(target = "user", source = "user")
    @Mapping(target = "quest", source = "quest")
    @Mapping(target = "id", ignore = true)
    UserQuest map(User user, Quest quest);



    @Mapping(target = "questRewards", source = "quest.rewards")
    @Mapping(target = "questExperience", source = "quest.experience")
    @Mapping(target = "questName", source = "quest.name")
    //@Mapping(target = "id", ignore = true)
    UserQuestResponse toResponse(UserQuest entity);
}
