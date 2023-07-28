package br.com.cwi.crescer.api.controller.dtos.request.logado;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
public class UpdateAvatarRequest {
    @NotNull
    private Long avatarId;
}
