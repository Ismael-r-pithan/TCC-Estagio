package br.com.cwi.crescer.api.service.user;

import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class GetUserAuthenticatedService {

    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    private GetIdUserAuthenticatedService getIdUserAuthenticatedService;


    public User get() {
        return usuarioRepository.findById(getIdUserAuthenticatedService.getId())
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Usuário não existe ou não está autenticado"));
    }
}
