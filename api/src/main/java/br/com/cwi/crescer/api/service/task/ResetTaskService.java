package br.com.cwi.crescer.api.service.task;

import br.com.cwi.crescer.api.domain.Task;
import br.com.cwi.crescer.api.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.time.LocalDate.now;

@Service
public class ResetTaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private GetTaskService getTaskService;


    @Transactional
    public void resetFinish(Long taskId) {

        Task task = getTaskService.getOwnedTask(taskId);

        task.setFinishedDate(null);

        taskRepository.save(task);
    }
}
