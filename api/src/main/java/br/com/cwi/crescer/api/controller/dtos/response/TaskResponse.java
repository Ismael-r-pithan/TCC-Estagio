package br.com.cwi.crescer.api.controller.dtos.response;

import br.com.cwi.crescer.api.domain.enums.Category;
import br.com.cwi.crescer.api.domain.enums.Priority;
import br.com.cwi.crescer.api.domain.enums.Visibility;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class TaskResponse {

    private Long id;
    private String name;
    private String description;
    private LocalDate scheduledDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Boolean recurring;
    private Visibility visibility;
    private Category category;
    private Priority priority;
    private LocalDate finishedDate;
}
