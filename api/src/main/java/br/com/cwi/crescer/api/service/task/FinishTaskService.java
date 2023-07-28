package br.com.cwi.crescer.api.service.task;

import br.com.cwi.crescer.api.domain.Task;
import br.com.cwi.crescer.api.repository.TaskRepository;
import br.com.cwi.crescer.api.validator.TaskNotFinishedValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.time.LocalDate.now;

@Service
public class FinishTaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskNotFinishedValidate taskNotFinishedValidate;

    @Autowired
    private GetTaskService getTaskService;

    @Autowired
    private CloneTaskService cloneTaskService;


    @Transactional
    public void finish(Long taskId) {

        Task task = getTaskService.getOwnedTask(taskId);

        taskNotFinishedValidate.validate(task);

        task.setFinishedDate(now());

        if(task.getRecurring()) {

            Task renewTask = cloneTaskService.clone(task);
            renewTask.setScheduledDate(renewTask.getScheduledDate().plusDays(7));
            renewTask.setFinishedDate(null);

            taskRepository.save(renewTask);
        }


        taskRepository.save(task);
    }
}
