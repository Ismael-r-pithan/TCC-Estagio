package br.com.cwi.crescer.api.service.me;

import br.com.cwi.crescer.api.domain.Friendship;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.domain.enums.Status;
import br.com.cwi.crescer.api.mapper.FriendshipMapper;
import br.com.cwi.crescer.api.repository.FriendshipRepository;
import br.com.cwi.crescer.api.repository.UserRepository;
import br.com.cwi.crescer.api.service.user.GetUserByIdService;
import br.com.cwi.crescer.api.service.user.GetUserAuthenticatedService;
import br.com.cwi.crescer.api.validator.SelfFriendshipValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class RequestFriendshipService {
    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private GetUserByIdService getUserByIdService;

    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    private FriendshipMapper friendshipMapper;

    @Autowired
    private ValidateFriendshipAlreadyExistsService validateFriendshipAlreadyExistsService;

    @Autowired
    private SelfFriendshipValidate selfFriendshipValidate;

    @Autowired
    private GetUserAuthenticatedService getUserAuthenticatedService;

    @Autowired
    private ValidateRequestFriendshipAlreadyExistsService validateRequestFriendshipAlreadyExistsService;

    @Transactional
    public void solicitar(Long friendId) {
        User me =  getUserAuthenticatedService.get();
        User amigo = getUserByIdService.get(friendId);

        validateFriendshipAlreadyExistsService.validate(me, amigo);
        selfFriendshipValidate.validate(me, amigo);
        validateRequestFriendshipAlreadyExistsService.validate(me, amigo);

        Friendship requestFriendship = friendshipMapper.toEntity(me, amigo);
        requestFriendship.setStatus(Status.PENDENTE);
        requestFriendship.setCreatedAt(LocalDateTime.now());

        friendshipRepository.save(requestFriendship);
    }
}
