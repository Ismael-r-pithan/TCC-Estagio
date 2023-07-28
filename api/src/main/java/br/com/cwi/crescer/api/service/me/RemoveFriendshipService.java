package br.com.cwi.crescer.api.service.me;

import br.com.cwi.crescer.api.domain.Friendship;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.repository.FriendshipRepository;
import br.com.cwi.crescer.api.service.user.GetUserByIdService;
import br.com.cwi.crescer.api.service.user.GetUserAuthenticatedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
public class RemoveFriendshipService {

    @Autowired
    private GetFriendshipByIdService getFriendshipByIdService;

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private GetUserAuthenticatedService getUserAuthenticatedService;

    @Autowired
    private GetUserByIdService getUserByIdService;

    @Transactional
    public void remove(Long id) {
        Friendship friendship = getFriendshipByIdService.get(id);
        User me = getUserAuthenticatedService.get();
        User friend = getUserByIdService.get(friendship.getFriend().getId());
        if (!Objects.equals(me.getId(), friendship.getUsuario().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Essa não é uma amizade sua");
        }
        friendshipRepository.deleteFriendship(me.getId(), friend.getId());
    }
}
