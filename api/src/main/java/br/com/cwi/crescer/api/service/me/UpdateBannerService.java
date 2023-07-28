package br.com.cwi.crescer.api.service.me;

import br.com.cwi.crescer.api.controller.dtos.request.logado.UpdateBannerRequest;
import br.com.cwi.crescer.api.domain.Item;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.repository.UserRepository;
import br.com.cwi.crescer.api.service.item.GetItemByIdService;
import br.com.cwi.crescer.api.service.item.ValidateIsBannerService;
import br.com.cwi.crescer.api.service.user.GetUserAuthenticatedService;
import br.com.cwi.crescer.api.validator.UserHasNoItemValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateBannerService {
    @Autowired
    private GetUserAuthenticatedService getUserAuthenticatedService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GetItemByIdService getItemByIdService;
    @Autowired
    private UserHasNoItemValidate userHasNoItemValidate;
    @Autowired
    private ValidateIsBannerService validateIsBannerService;

    public void update(UpdateBannerRequest request) {
        User logado = getUserAuthenticatedService.get();

        userHasNoItemValidate.validate(logado, request.getBannerId());
        validateIsBannerService.validate(request.getBannerId());
        Item banner = getItemByIdService.get(request.getBannerId());

        logado.setBannerUrl(banner.getImageUrl());

        userRepository.save(logado);
    }
}
