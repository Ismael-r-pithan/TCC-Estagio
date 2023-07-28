package br.com.cwi.crescer.api.service.me;

import br.com.cwi.crescer.api.domain.Friendship;
import br.com.cwi.crescer.api.repository.FriendshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class GetFriendshipByIdService {
    @Autowired
    private FriendshipRepository friendshipRepository;

    public Friendship get(Long id) {
        return friendshipRepository.findById(id).orElseThrow(() -> new ResponseStatusException((NOT_FOUND), "Amizade não encontrada"));
    }
}
