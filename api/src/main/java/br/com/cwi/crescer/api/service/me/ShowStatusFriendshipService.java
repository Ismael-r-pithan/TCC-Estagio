package br.com.cwi.crescer.api.service.me;

import br.com.cwi.crescer.api.domain.Friendship;
import br.com.cwi.crescer.api.domain.enums.Status;
import br.com.cwi.crescer.api.repository.FriendshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ShowStatusFriendshipService {
    @Autowired
    private FriendshipRepository friendshipRepository;
    public Status showStatus(Long friendshipId) {
        Friendship friendship = friendshipRepository.findById(friendshipId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Amizade não encontrada"));

        return friendship.getStatus();
    }
}
