package br.com.cwi.crescer.api.service.me;

import br.com.cwi.crescer.api.controller.dtos.response.FriendshipResponse;
import br.com.cwi.crescer.api.mapper.FriendshipMapper;
import br.com.cwi.crescer.api.repository.FriendshipRepository;
import br.com.cwi.crescer.api.service.user.GetUserAuthenticatedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListFriendsService {
    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private FriendshipMapper friendshipMapper;

    @Autowired
    private GetUserAuthenticatedService getUserAuthenticatedService;


    public Page<FriendshipResponse> list(Pageable pageable, String search) {
        Long usuarioLogadoId = getUserAuthenticatedService.get().getId();

        if (search != null) {
            return friendshipRepository.findByUserIdWithFiltro(usuarioLogadoId, pageable, search).map(friend -> friendshipMapper.toResponse(friend));
        }

        return friendshipRepository.findByUserId(usuarioLogadoId, pageable).map(friend -> friendshipMapper.toResponse(friend));
    }
}
