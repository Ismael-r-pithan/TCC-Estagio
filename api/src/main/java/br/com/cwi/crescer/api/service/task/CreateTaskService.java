package br.com.cwi.crescer.api.service.task;

import br.com.cwi.crescer.api.controller.dtos.request.task.CreateTaskRequest;
import br.com.cwi.crescer.api.controller.dtos.response.TaskResponse;
import br.com.cwi.crescer.api.domain.Task;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.mapper.TaskMapper;
import br.com.cwi.crescer.api.repository.TaskRepository;
import br.com.cwi.crescer.api.service.user.GetUserAuthenticatedService;
import br.com.cwi.crescer.api.validator.ScheduledDateValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateTaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private GetUserAuthenticatedService getUserAuthenticatedService;

    @Autowired
    private ValidateTaskConflictService validateTaskConflictService;

    @Autowired
    private ScheduledDateValidate scheduledDateValidate;


    @Transactional
    public TaskResponse create(CreateTaskRequest request) {

        User me = getUserAuthenticatedService.get();

        Task task = taskMapper.toEntity(request);
        task.setAutor(me);
        task.setFinishedDate(null);

        validateTaskConflictService.validate(task);
        scheduledDateValidate.validate(task.getScheduledDate());

        taskRepository.save(task);

        return taskMapper.toResponse(task);
    }
}
