package br.com.cwi.crescer.api.mapper;


import br.com.cwi.crescer.api.controller.dtos.request.task.CreateTaskRequest;
import br.com.cwi.crescer.api.controller.dtos.response.TaskResponse;
import br.com.cwi.crescer.api.domain.Task;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;


@Mapper
@Component
public interface TaskMapper {

    Task toEntity(CreateTaskRequest request);

    TaskResponse toResponse(Task entity);


}
