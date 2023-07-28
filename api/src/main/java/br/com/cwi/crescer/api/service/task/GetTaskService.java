package br.com.cwi.crescer.api.service.task;

import br.com.cwi.crescer.api.controller.dtos.response.TaskResponse;
import br.com.cwi.crescer.api.domain.Task;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.mapper.TaskMapper;
import br.com.cwi.crescer.api.repository.TaskRepository;
import br.com.cwi.crescer.api.service.user.GetUserAuthenticatedService;
import br.com.cwi.crescer.api.validator.TaskOwnedUserValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class GetTaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private GetUserAuthenticatedService getUserAuthenticatedService;

    @Autowired
    private TaskOwnedUserValidate taskOwnedUserValidate;

    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new ResponseStatusException((NOT_FOUND), "Tarefa não encontrada"));
    }

    public TaskResponse getResponse(Long id) {
        Task task = getOwnedTask(id);
        return taskMapper.toResponse(task);
    }

    public Task getOwnedTask(Long id) {
        User me = getUserAuthenticatedService.get();
        Task task = findById(id);

        taskOwnedUserValidate.validate(me, task);

        return task;
    }
}
