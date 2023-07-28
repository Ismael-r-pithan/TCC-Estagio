package br.com.cwi.crescer.api.controller.dtos.request.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ForgotPasswordRequest {
    @Email
    @NotBlank
    private String email;

}
