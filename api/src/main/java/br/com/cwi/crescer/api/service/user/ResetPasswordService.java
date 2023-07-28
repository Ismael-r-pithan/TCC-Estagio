package br.com.cwi.crescer.api.service.user;

import br.com.cwi.crescer.api.domain.PasswordResetToken;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.repository.PasswordResetTokenRepository;
import br.com.cwi.crescer.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ResetPasswordService {
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public void resetPassword(String token, String newPassword) {

        Optional<PasswordResetToken> passwordResetToken = passwordResetTokenRepository.findByToken(token);

        PasswordResetToken validPasswordResetToken = passwordResetToken.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token não foi encontrado"));


        if (validPasswordResetToken.getExpiresIn().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token expirou");
        }

        User user = validPasswordResetToken.getUser();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        usuarioRepository.save(user);
        passwordResetTokenRepository.delete(validPasswordResetToken);
    }
}
