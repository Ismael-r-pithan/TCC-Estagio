package br.com.cwi.crescer.api.controller.dtos.request.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActivateAccountRequest {

    @NotBlank
    private String email;
}
