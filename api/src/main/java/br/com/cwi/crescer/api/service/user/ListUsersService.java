package br.com.cwi.crescer.api.service.user;

import br.com.cwi.crescer.api.controller.dtos.response.UserResponse;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.mapper.UserMapper;
import br.com.cwi.crescer.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListUsersService {
    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    private GetUserAuthenticatedService getUserAuthenticatedService;

    @Autowired
    private UserMapper usuarioMapper;

    public Page<UserResponse> list(Pageable pageable, String search) {
        User usuarioLogado = getUserAuthenticatedService.get();

        if (search != null) {
            return usuarioRepository.findByNameOrEmail(pageable, search, usuarioLogado.getId()).map(usuarioMapper::toResponse);
        }
        return usuarioRepository.findByEmailNot(usuarioLogado.getEmail(), pageable).map(usuarioMapper::toResponse);
    }
}
