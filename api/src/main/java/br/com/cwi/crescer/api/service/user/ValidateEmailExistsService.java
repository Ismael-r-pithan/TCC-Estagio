package br.com.cwi.crescer.api.service.user;

import br.com.cwi.crescer.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.http.HttpStatus.CONFLICT;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ValidateEmailExistsService {
    @Autowired
    private UserRepository usuarioRepository;

    public void validate(String email) {
        if(usuarioRepository.existsByEmail(email)){
            throw new ResponseStatusException(CONFLICT, "E-mail já cadastrado");
        }
    }
}
