package br.com.cwi.crescer.api.service.user;

import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class GetUserByEmailService {
    @Autowired
    private UserRepository usuarioRepository;

    public User get(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Usuário não encontrado"));
    }
}
