package br.com.cwi.crescer.api.service.user;

import br.com.cwi.crescer.api.controller.dtos.response.UserResponse;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.mapper.UserMapper;
import br.com.cwi.crescer.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class GetUserByIdService {
    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    private UserMapper userMapper;

    public User get(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Usuário não encontrado"));
    }

    public UserResponse getResponse(Long id) {
        return userMapper.toResponse(get(id));
    }
}