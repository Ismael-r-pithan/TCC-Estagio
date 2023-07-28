package br.com.cwi.crescer.api.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendPasswordResetEmailService {

    @Autowired
    private JavaMailSender mailSender;
    public void sendPasswordResetEmail(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Reset de senha");
        message.setText("Para resetar sua senha, clique no seguinte link: "
                + "http://localhost:3000/reset-password?token=" + token);
        message.setTo(email);
        mailSender.send(message);
    }
}
