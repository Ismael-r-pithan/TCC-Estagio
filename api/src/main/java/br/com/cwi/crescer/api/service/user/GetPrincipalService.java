package br.com.cwi.crescer.api.service.user;

import br.com.cwi.crescer.api.controller.dtos.response.UserResponse;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetPrincipalService {
    @Autowired
    private GetUserAuthenticatedService getUserAuthenticatedService;

    @Autowired
    private UserMapper usuarioMapper;

    public UserResponse get() {
        User usuarioAutenticado = getUserAuthenticatedService.get();
        return usuarioMapper.toResponse(usuarioAutenticado);
    }
}
