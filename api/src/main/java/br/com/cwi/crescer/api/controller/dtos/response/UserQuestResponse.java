package br.com.cwi.crescer.api.controller.dtos.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class UserQuestResponse {
    private String questName;
    private Integer questRewards;
    private Long questExperience;
    private LocalDate completedAt;
}
