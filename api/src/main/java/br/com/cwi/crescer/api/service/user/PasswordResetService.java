package br.com.cwi.crescer.api.service.user;

import br.com.cwi.crescer.api.domain.PasswordResetToken;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.repository.PasswordResetTokenRepository;
import br.com.cwi.crescer.api.repository.UserRepository;
import br.com.cwi.crescer.api.shared.GenerateToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PasswordResetService {

    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;


    @Autowired
    private SendPasswordResetEmailService sendPasswordResetEmailService;

    private static final long TOKEN_EXPIRES_IN = 1L;

    public void requestPasswordReset(String email) {
        Optional<User> user = usuarioRepository.findByEmail(email);

        User validUser = user.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        String token = GenerateToken.generate();
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setUser(validUser);
        passwordResetToken.setToken(token);
        passwordResetToken.setExpiresIn(LocalDateTime.now().plusDays(TOKEN_EXPIRES_IN));
        passwordResetTokenRepository.save(passwordResetToken);
        sendPasswordResetEmailService.sendPasswordResetEmail(validUser.getEmail(), token);
    }

}
