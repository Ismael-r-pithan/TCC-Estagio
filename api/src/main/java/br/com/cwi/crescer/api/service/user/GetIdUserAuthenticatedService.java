package br.com.cwi.crescer.api.service.user;

import br.com.cwi.crescer.api.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class GetIdUserAuthenticatedService {

    @Autowired
    private SendMailActivateAccountService sendMailActivateAccountService;
    public Long getId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userSecurity = (UserDetailsImpl) authentication.getPrincipal();
        return userSecurity.getId();
    }
}
