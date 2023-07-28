package br.com.cwi.crescer.api.service.user;

import br.com.cwi.crescer.api.controller.dtos.request.user.ActivateAccountRequest;
import br.com.cwi.crescer.api.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResendMailActivateAccountService {

    @Autowired
    private SendMailActivateAccountService sendMailActivateAccountService;
    @Autowired
    private GetUserByEmailService getUserByEmailService;

    public void send(ActivateAccountRequest request) {

        User user = getUserByEmailService.get(request.getEmail());

        sendMailActivateAccountService.send(request.getEmail());
    }
}
