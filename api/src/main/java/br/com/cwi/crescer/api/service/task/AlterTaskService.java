package br.com.cwi.crescer.api.service.task;

import br.com.cwi.crescer.api.controller.dtos.request.task.AlterTaskRequest;
import br.com.cwi.crescer.api.domain.Task;
import br.com.cwi.crescer.api.repository.TaskRepository;
import br.com.cwi.crescer.api.validator.ScheduledDateValidate;
import br.com.cwi.crescer.api.validator.AlterTaskRequestValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class AlterTaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private GetTaskService getTaskService;

    @Autowired
    private AlterTaskRequestValidate alterTaskRequestValidate;

    @Autowired
    private ScheduledDateValidate scheduledDateValidate;

    @Transactional
    public void alter(Long taskId, AlterTaskRequest request) {
        Task task = getTaskService.getOwnedTask(taskId);

        alterTaskRequestValidate.validate(request);

        alterTaskChanges(task, request);

        scheduledDateValidate.validate(task.getScheduledDate());

        taskRepository.save(task);
    }

    private void alterTaskChanges(Task task, AlterTaskRequest request){
        if (Objects.nonNull(request.getName()))
            task.setName(request.getName());

        if (Objects.nonNull(request.getDescription()))
            task.setDescription(request.getDescription());

        if (Objects.nonNull(request.getScheduledDate()))
            task.setScheduledDate(request.getScheduledDate());

        if (Objects.nonNull(request.getStartTime()))
            task.setStartTime(request.getStartTime());

        if (Objects.nonNull(request.getEndTime()))
            task.setEndTime(request.getEndTime());

        if (Objects.nonNull(request.getRecurring()))
            task.setRecurring(request.getRecurring());

        if (Objects.nonNull(request.getVisibility()))
            task.setVisibility(request.getVisibility());

        if (Objects.nonNull(request.getCategory()))
            task.setCategory(request.getCategory());

        if (Objects.nonNull(request.getPriority()))
            task.setPriority(request.getPriority());
    }
}
