package br.com.cwi.crescer.api.service.item;

import br.com.cwi.crescer.api.controller.dtos.request.item.BuyItemRequest;
import br.com.cwi.crescer.api.domain.Item;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.repository.UserRepository;
import br.com.cwi.crescer.api.service.user.GetUserAuthenticatedService;
import br.com.cwi.crescer.api.validator.HasMoneyValidate;
import br.com.cwi.crescer.api.validator.UserAlreadyOwnsItemValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuyItemService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GetItemByIdService getItemByIdService;

    @Autowired
    private HasMoneyValidate hasMoneyValidate;

    @Autowired
    private UserAlreadyOwnsItemValidate userAlreadyOwnsItemValidate;

    @Autowired
    private GetUserAuthenticatedService getUserAuthenticatedService;
    public void buyItem(BuyItemRequest request) {
        User logado = getUserAuthenticatedService.get();
        Item item = getItemByIdService.get(request.getId());

        userAlreadyOwnsItemValidate.validate(logado, item.getId());
        hasMoneyValidate.validate(logado, item);

        logado.setMoney(logado.getMoney() - item.getPrice());
        logado.buyItem(item);

        userRepository.save(logado);
    }
}
