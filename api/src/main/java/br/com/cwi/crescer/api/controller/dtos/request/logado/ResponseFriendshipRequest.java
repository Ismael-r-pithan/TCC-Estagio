package br.com.cwi.crescer.api.controller.dtos.request.logado;

import br.com.cwi.crescer.api.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ResponseFriendshipRequest {
    @NotNull
    private Status response;
    @NotNull
    private Long idRequest;
}
