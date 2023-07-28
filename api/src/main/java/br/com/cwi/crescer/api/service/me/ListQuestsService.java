package br.com.cwi.crescer.api.service.me;

import br.com.cwi.crescer.api.controller.dtos.response.UserQuestResponse;
import br.com.cwi.crescer.api.mapper.UserQuestMapper;
import br.com.cwi.crescer.api.repository.UserQuestRepository;
import br.com.cwi.crescer.api.service.user.GetUserAuthenticatedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListQuestsService {

    @Autowired
    private UserQuestRepository userQuestRepository;

    @Autowired
    private UserQuestMapper userQuestMapper;

    @Autowired
    private GetUserAuthenticatedService getUserAuthenticatedService;


    public Page<UserQuestResponse> list(Pageable pageable) {
        Long usuarioLogadoId = getUserAuthenticatedService.get().getId();

        return userQuestRepository.findQuestsByUserId(pageable, usuarioLogadoId).map(uq -> userQuestMapper.toResponse(uq));
    }
}
