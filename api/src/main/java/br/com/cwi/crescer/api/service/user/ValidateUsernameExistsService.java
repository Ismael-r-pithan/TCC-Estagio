package br.com.cwi.crescer.api.service.user;

import br.com.cwi.crescer.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.CONFLICT;

@Service
public class ValidateUsernameExistsService {
    @Autowired
    private UserRepository usuarioRepository;

    public void validate(String username) {
        if(usuarioRepository.existsByUsername(username)){
            throw new ResponseStatusException(CONFLICT, "Username já cadastrado");
        }
    }
}
