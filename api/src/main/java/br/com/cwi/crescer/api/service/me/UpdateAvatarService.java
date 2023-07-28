package br.com.cwi.crescer.api.service.me;

import br.com.cwi.crescer.api.controller.dtos.request.logado.UpdateAvatarRequest;
import br.com.cwi.crescer.api.domain.Item;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.repository.UserRepository;
import br.com.cwi.crescer.api.service.item.GetItemByIdService;
import br.com.cwi.crescer.api.service.item.ValidateIsAvatarService;
import br.com.cwi.crescer.api.service.user.GetUserAuthenticatedService;
import br.com.cwi.crescer.api.validator.UserHasNoItemValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateAvatarService {

    @Autowired
    private GetUserAuthenticatedService getUserAuthenticatedService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GetItemByIdService getItemByIdService;
    @Autowired
    private UserHasNoItemValidate userHasNoItemValidate;
    @Autowired
    private ValidateIsAvatarService validateIsAvatarService;

    public void update(UpdateAvatarRequest request) {
        User logado = getUserAuthenticatedService.get();

        userHasNoItemValidate.validate(logado, request.getAvatarId());
        validateIsAvatarService.validate(request.getAvatarId());
        Item avatar = getItemByIdService.get(request.getAvatarId());

        logado.setImageProfile(avatar.getImageUrl());

        userRepository.save(logado);
    }
}
