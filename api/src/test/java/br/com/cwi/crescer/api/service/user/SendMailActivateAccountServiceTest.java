package br.com.cwi.crescer.api.service.user;

import br.com.cwi.crescer.api.domain.User;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SendMailActivateAccountServiceTest {

    @Mock
    private GetUserByEmailService getUserByEmailService;

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private SendMailActivateAccountService sendMailActivateAccountService;

    @Test
    @DisplayName("Deve enviar email de ativação de conta")
    void deveEnviarEmailDeAtivacaoDeConta() {
        // Mock de usuário
        User usuario = new User();
        usuario.setEmail("usuario@teste.com");
        usuario.setConfirmToken("token123");

        // Configuração do mock do serviço GetUserByEmailService
        when(getUserByEmailService.get(anyString())).thenReturn(usuario);

        // Executa o método a ser testado
        sendMailActivateAccountService.send(usuario.getEmail());

        // Verifica se o método JavaMailSender#send foi chamado com um objeto SimpleMailMessage correto
        verify(mailSender, times(1)).send(argThat((SimpleMailMessage message) ->
                message.getSubject().equals("Ativação de conta")
                        && message.getText().contains(usuario.getConfirmToken())
                        && message.getText().contains(usuario.getEmail())
                        && message.getTo()[0].equals(usuario.getEmail())
        ));
    }
}
