package br.com.cwi.crescer.api.service.user;

import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class ActivateAccountService {
    @Autowired
    private GetUserByEmailService getUserByEmailService;

    @Autowired
    private UserRepository usuarioRepository;

    public void activate(String token, String email) {
        User usuario = getUserByEmailService.get(email);

        if (!usuario.getConfirmToken().equals(token)) {
            throw new ResponseStatusException(BAD_REQUEST, "Token inválido para o usuário");
        }

        usuario.setAtivo(true);

        usuarioRepository.save(usuario);
    }
}
