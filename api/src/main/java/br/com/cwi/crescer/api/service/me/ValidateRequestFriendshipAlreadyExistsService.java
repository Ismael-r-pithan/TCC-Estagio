package br.com.cwi.crescer.api.service.me;

import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.repository.FriendshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ValidateRequestFriendshipAlreadyExistsService {
    private static final String MESSAGE_ERROR_REQUEST_FRIENDSHIP_ALREADY_EXISTS = "Amizade já foi solicitada";

    @Autowired
    private FriendshipRepository friendshipRepository;

    public void validate(User me, User amigo) {
        if (friendshipRepository.existsRequestFriendship(me, amigo)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MESSAGE_ERROR_REQUEST_FRIENDSHIP_ALREADY_EXISTS);
        }
    }
}
