package br.com.cwi.crescer.api.controller.dtos.request.task;

import br.com.cwi.crescer.api.domain.enums.Category;
import br.com.cwi.crescer.api.domain.enums.Priority;
import br.com.cwi.crescer.api.domain.enums.Visibility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private LocalDate scheduledDate;
    @NotNull
    private LocalTime startTime;
    @NotNull
    private LocalTime endTime;
    @NotNull
    private Boolean recurring;
    @NotNull
    private Visibility visibility;
    @NotNull
    private Category category;
    @NotNull
    private Priority priority;
}
