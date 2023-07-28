package br.com.cwi.crescer.api.mapper;


import br.com.cwi.crescer.api.controller.dtos.request.user.CreateUserRequest;
import br.com.cwi.crescer.api.controller.dtos.response.UserResponse;
import br.com.cwi.crescer.api.domain.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;


@Mapper
@Component
public interface UserMapper {

    User toEntity(CreateUserRequest request);

    UserResponse toResponse(User entity);


}
