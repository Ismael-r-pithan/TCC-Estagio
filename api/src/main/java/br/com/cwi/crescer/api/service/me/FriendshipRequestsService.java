package br.com.cwi.crescer.api.service.me;

import br.com.cwi.crescer.api.controller.dtos.response.FriendshipResponse;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.mapper.FriendshipMapper;
import br.com.cwi.crescer.api.repository.FriendshipRepository;
import br.com.cwi.crescer.api.service.user.GetUserAuthenticatedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendshipRequestsService {
    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private GetUserAuthenticatedService getUserAuthenticatedService;

    @Autowired
    private FriendshipMapper friendshipMapper;

    public List<FriendshipResponse> list() {
        User user = getUserAuthenticatedService.get();
        return friendshipRepository.findByUserIdWithAmizadePendente(user.getId()).stream().map(friend -> friendshipMapper.toResponse(friend)).collect(Collectors.toList());
    }

}
