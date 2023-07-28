package br.com.cwi.crescer.api.service.task;

import br.com.cwi.crescer.api.domain.Task;
import br.com.cwi.crescer.api.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RemoveTaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private GetTaskService getTaskService;


    @Transactional
    public void remove(Long taskId) {
        Task task = getTaskService.getOwnedTask(taskId);

        taskRepository.delete(task);
    }
}
