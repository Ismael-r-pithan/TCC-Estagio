package br.com.cwi.crescer.api.service.task;

import br.com.cwi.crescer.api.domain.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CloneTaskService {

    public Task clone(Task task) {
        Task clone = new Task();

        BeanUtils.copyProperties(task, clone);

        clone.setId(null);

        return clone;
    }
}

