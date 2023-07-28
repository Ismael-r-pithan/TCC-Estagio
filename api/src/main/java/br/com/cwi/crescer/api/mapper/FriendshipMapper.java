package br.com.cwi.crescer.api.mapper;


import br.com.cwi.crescer.api.controller.dtos.response.FriendshipResponse;
import br.com.cwi.crescer.api.domain.Friendship;
import br.com.cwi.crescer.api.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface FriendshipMapper {
    @Mapping(source = "usuario.id", target = "idFriend")
    @Mapping(source = "usuario.username", target = "usernameFriend")
    @Mapping(source = "friendship.id", target = "idRequest")
    @Mapping(source = "usuario.imageProfile", target = "imageProfileFriend")
    @Mapping(source = "usuario.experience", target = "experienceFriend")
    FriendshipResponse toResponse(Friendship friendship);

    @Mapping(source = "usuario", target = "usuario")
    @Mapping(source = "friend", target = "friend")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Friendship toEntity(User usuario, User friend);
}
