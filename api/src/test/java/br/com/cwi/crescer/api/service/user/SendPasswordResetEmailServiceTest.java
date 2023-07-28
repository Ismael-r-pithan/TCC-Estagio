package br.com.cwi.crescer.api.service.user;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;

@SpringBootTest
public class SendPasswordResetEmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private SendPasswordResetEmailService sendPasswordResetEmailService;

    @Test
    public void testSendPasswordResetEmail() {
        String email = "teste@teste.com";
        String token = "123456";

        sendPasswordResetEmailService.sendPasswordResetEmail(email, token);

        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        Mockito.verify(mailSender, times(1)).send(messageCaptor.capture());

        SimpleMailMessage message = messageCaptor.getValue();
        assertEquals("Reset de senha", message.getSubject());
        assertEquals(email, Objects.requireNonNull(message.getTo())[0]);
        assertTrue(Objects.requireNonNull(message.getText()).contains(token));
        assertTrue(message.getText().contains("http://localhost:3000/reset-password"));
    }

}
