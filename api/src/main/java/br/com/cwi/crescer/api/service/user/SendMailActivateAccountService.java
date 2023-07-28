package br.com.cwi.crescer.api.service.user;


import br.com.cwi.crescer.api.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendMailActivateAccountService {
    @Autowired
    private GetUserByEmailService getUserByEmailService;

    @Autowired
    private JavaMailSender mailSender;

    public void send(String email) {
        User usuario = getUserByEmailService.get(email);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Ativação de conta");
        message.setText("Para ativar sua conta, clique no seguinte link: "
                + "http://localhost:3000/users/activate-account?token=" + usuario.getConfirmToken() +"&email=" + usuario.getEmail());
        message.setTo(usuario.getEmail());
        mailSender.send(message);
    }

}
