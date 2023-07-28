package br.com.cwi.crescer.api.controller.dtos.request.item;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor @Builder
public class BuyItemRequest {

    @NotNull
    private Long id;
}
