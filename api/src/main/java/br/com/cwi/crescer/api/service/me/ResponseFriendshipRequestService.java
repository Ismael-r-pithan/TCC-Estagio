package br.com.cwi.crescer.api.service.me;

import br.com.cwi.crescer.api.controller.dtos.request.logado.ResponseFriendshipRequest;
import br.com.cwi.crescer.api.domain.Friendship;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.domain.enums.Status;
import br.com.cwi.crescer.api.mapper.FriendshipMapper;
import br.com.cwi.crescer.api.repository.FriendshipRepository;
import br.com.cwi.crescer.api.service.user.GetUserAuthenticatedService;
import br.com.cwi.crescer.api.validator.UserWhoRepliedValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class ResponseFriendshipRequestService {

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private GetFriendshipByIdService getFriendshipByIdService;

    @Autowired
    private FriendshipMapper friendshipMapper;

    @Autowired
    private UserWhoRepliedValidate userWhoRepliedValidate;

    @Autowired
    private GetUserAuthenticatedService getUserAuthenticatedService;

    @Transactional
    public void response(ResponseFriendshipRequest request) {
        Friendship requestFriendship = getFriendshipByIdService.get(request.getIdRequest());

        User me = getUserAuthenticatedService.get();

        userWhoRepliedValidate.validate(me, requestFriendship.getFriend());

        if(request.getResponse().equals(Status.REJEITADA)) {
            friendshipRepository.delete(requestFriendship);
        } else {
            requestFriendship.setStatus(request.getResponse());
            requestFriendship.setCreatedAt(LocalDateTime.now());
            friendshipRepository.save(requestFriendship);

            Friendship newFriend = friendshipMapper.toEntity(requestFriendship.getFriend(), requestFriendship.getUsuario());
            newFriend.setStatus(Status.ACEITA);
            newFriend.setCreatedAt(LocalDateTime.now());
            friendshipRepository.save(newFriend);
        }
    }
}
