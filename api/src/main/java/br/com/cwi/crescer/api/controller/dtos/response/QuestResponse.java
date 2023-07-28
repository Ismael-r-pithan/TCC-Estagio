package br.com.cwi.crescer.api.controller.dtos.response;

import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class QuestResponse {
    private Long id;
    private String name;
    private String description;
    private Integer rewards;
    private String recurring;
}
