package br.com.cwi.crescer.api.controller;

import br.com.cwi.crescer.api.controller.dtos.request.user.ActivateAccountRequest;
import br.com.cwi.crescer.api.controller.dtos.request.user.ForgotPasswordRequest;
import br.com.cwi.crescer.api.controller.dtos.request.user.PasswordResetRequest;
import br.com.cwi.crescer.api.controller.dtos.response.UserResponse;
import br.com.cwi.crescer.api.service.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.OK;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@ComponentScan
public class AuthController {
    @Autowired
    private GetPrincipalService getPrincipalService;

    @Autowired
    private PasswordResetService passwordResetService;

    @Autowired
    private ResetPasswordService resetPasswordService;

    @Autowired
    private ActivateAccountService activateAccountService;

    @Autowired
    private ResendMailActivateAccountService resendMailActivateAccountService;


    @PostMapping("/login")
    public ResponseEntity<UserResponse> login() {
        return ResponseEntity.status(OK).body(getPrincipalService.get());
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return ResponseEntity.status(OK).build();
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Object> forgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        passwordResetService.requestPasswordReset(forgotPasswordRequest.getEmail());
        return ResponseEntity.ok("Um email com instruções para redefinir sua senha foi enviado para "
                + forgotPasswordRequest.getEmail());
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Object> resetPassword(@RequestParam String token,
                                           @Valid @RequestBody PasswordResetRequest passwordResetRequest) {
        resetPasswordService.resetPassword(token, passwordResetRequest.getPassword());
        return ResponseEntity.ok("Senha redefinida com sucesso!");
    }

    @GetMapping("/activate-account")
    public ResponseEntity<Void> activateAccount(@RequestParam String token, @RequestParam String email) {
        activateAccountService.activate(token, email);
        return ResponseEntity.status(OK).build();
    }

    @PostMapping("/activate-account")
    public ResponseEntity<Void> resendMailactivateAccount(@Valid @RequestBody ActivateAccountRequest request) {
        resendMailActivateAccountService.send(request);
        return ResponseEntity.status(OK).build();
    }

}
